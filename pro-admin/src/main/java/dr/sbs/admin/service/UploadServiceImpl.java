package dr.sbs.admin.service;

import cn.hutool.core.io.FileUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import javax.activation.MimetypesFileTypeMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** 上传管理Service实现类 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
  @Value("${qcloudCos.secretId}")
  private String cosSecretId;

  @Value("${qcloudCos.secretKey}")
  private String cosSecretKey;

  @Value("${qcloudCos.bucket}")
  private String cosBucket;

  @Value("${qcloudCos.region}")
  private String cosRegion;

  @Value("${qcloudCos.domain}")
  private String cosDomain;

  @Override
  public String uploadFile(MultipartFile file) {
    try {
      String extName = FileUtil.extName(file.getOriginalFilename());
      String filename = UUID.randomUUID().toString() + (extName != null ? "." + extName : "");
      String key = "u/" + filename;

      ObjectMetadata objectMetadata = new ObjectMetadata();
      // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
      objectMetadata.setContentLength(file.getSize());
      // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
      objectMetadata.setContentType(file.getContentType());

      return uploadInternal(file.getInputStream(), objectMetadata, key);
    } catch (Exception e) {
      log.error("上传发生错误: {}！", e.getMessage());
    }
    return null;
  }

  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  @Override
  public String uploadUrl(String url) {
    try {
      String extName = FileUtil.extName(url);
      String filename =
          UUID.randomUUID().toString()
              + (extName != null && !extName.equals("") ? "." + extName : ".jpg");
      String key = "u/" + filename;

      URL url1 = new URL(url);

      HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
      httpURLConnection.setConnectTimeout(3000);
      httpURLConnection.setDoInput(true);
      httpURLConnection.setRequestMethod("GET");

      if (httpURLConnection.getResponseCode() != 200) return null;

      ObjectMetadata objectMetadata = new ObjectMetadata();
      // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
      objectMetadata.setContentLength(httpURLConnection.getContentLength());
      // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
      objectMetadata.setContentType(httpURLConnection.getContentType());

      return uploadInternal(httpURLConnection.getInputStream(), objectMetadata, key);
    } catch (Exception e) {
      log.error("上传发生错误: {}！", e.getMessage());
    }
    return null;
  }

  @Override
  public String uploadLocalFile(File file) {
    try {
      String extName = FileUtil.extName(file.getName());
      String filename = UUID.randomUUID().toString() + (extName != null ? "." + extName : "");
      String key = "u/" + filename;

      ObjectMetadata objectMetadata = new ObjectMetadata();
      // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
      objectMetadata.setContentType(new MimetypesFileTypeMap().getContentType(filename));

      return uploadInternal(new FileInputStream(file), objectMetadata, key);
    } catch (Exception e) {
      log.error("上传发生错误: {}！", e.getMessage());
    }
    return null;
  }

  private String uploadInternal(
      InputStream inputStream, ObjectMetadata objectMetadata, String key) {
    try {
      // 1 初始化用户身份信息(secretId, secretKey)
      COSCredentials cred = new BasicCOSCredentials(cosSecretId, cosSecretKey);
      // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/dishuct/436/6224
      ClientConfig clientConfig = new ClientConfig(new Region(cosRegion));
      // 3 生成cos客户端
      COSClient cosclient = new COSClient(cred, clientConfig);
      // bucket名需包含appid
      String bucketName = cosBucket;

      PutObjectRequest putObjectRequest =
          new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
      // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
      putObjectRequest.setStorageClass(StorageClass.Standard_IA);

      PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
      // putobjectResult会返回文件的etag
      String etag = putObjectResult.getETag();

      // 关闭客户端
      cosclient.shutdown();

      return cosDomain + "/" + key;
    } catch (Exception e) {
      log.error("上传发生错误: {}！", e.getMessage());
    }
    return null;
  }
}
