package dr.sbs.admin.service;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

/** 上传管理Service */
public interface UploadService {
  /** 上传文件 */
  String uploadFile(MultipartFile file);

  /** 上传Url */
  String uploadUrl(String url);

  /** 上传本地文件 */
  String uploadLocalFile(File file);
}
