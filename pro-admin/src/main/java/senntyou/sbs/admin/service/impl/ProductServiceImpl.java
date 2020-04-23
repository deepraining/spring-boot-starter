package senntyou.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import senntyou.sbs.admin.dao.*;
import senntyou.sbs.admin.dto.ProductParam;
import senntyou.sbs.admin.dto.ProductQueryParam;
import senntyou.sbs.admin.dto.ProductResult;
import senntyou.sbs.admin.service.ProductService;
import senntyou.sbs.mbg.mapper.*;
import senntyou.sbs.mbg.model.Product;
import senntyou.sbs.mbg.model.ProductAttributeValueExample;
import senntyou.sbs.mbg.model.ProductExample;
import senntyou.sbs.mbg.model.ProductVerifyRecord;

/** 商品管理Service实现类 Created by macro on 2018/4/26. */
@Service
public class ProductServiceImpl implements ProductService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
  @Autowired private ProductMapper productMapper;
  @Autowired private ProductAttributeValueDao productAttributeValueDao;
  @Autowired private ProductAttributeValueMapper productAttributeValueMapper;
  @Autowired private ProductDao productDao;
  @Autowired private ProductVerifyRecordDao productVerifyRecordDao;

  @Override
  public int create(ProductParam productParam) {
    int count;
    // 创建商品
    Product product = productParam;
    product.setId(null);
    productMapper.insertSelective(product);
    count = 1;
    return count;
  }

  @Override
  public ProductResult getUpdateInfo(Long id) {
    return productDao.getUpdateInfo(id);
  }

  @Override
  public int update(Long id, ProductParam productParam) {
    int count;
    // 更新商品信息
    Product product = productParam;
    product.setId(id);
    productMapper.updateByPrimaryKeySelective(product);
    // 修改商品参数,添加自定义商品规格
    ProductAttributeValueExample productAttributeValueExample = new ProductAttributeValueExample();
    productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
    productAttributeValueMapper.deleteByExample(productAttributeValueExample);
    relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);
    count = 1;
    return count;
  }

  @Override
  public List<Product> list(
      ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    ProductExample productExample = new ProductExample();
    ProductExample.Criteria criteria = productExample.createCriteria();
    criteria.andDeleteStatusEqualTo(0);
    if (productQueryParam.getPublishStatus() != null) {
      criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
    }
    if (productQueryParam.getVerifyStatus() != null) {
      criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
    }
    if (!StringUtils.isEmpty(productQueryParam.getKeyword())) {
      criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
    }
    if (!StringUtils.isEmpty(productQueryParam.getProductSn())) {
      criteria.andProductSnEqualTo(productQueryParam.getProductSn());
    }
    if (productQueryParam.getBrandId() != null) {
      criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
    }
    if (productQueryParam.getProductCategoryId() != null) {
      criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
    }
    return productMapper.selectByExample(productExample);
  }

  @Override
  public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
    Product product = new Product();
    product.setVerifyStatus(verifyStatus);
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    List<ProductVerifyRecord> list = new ArrayList<>();
    int count = productMapper.updateByExampleSelective(product, example);
    // 修改完审核状态后插入审核记录
    for (Long id : ids) {
      ProductVerifyRecord record = new ProductVerifyRecord();
      record.setProductId(id);
      record.setCreateTime(new Date());
      record.setDetail(detail);
      record.setStatus(verifyStatus);
      record.setVerifyMan("test");
      list.add(record);
    }
    productVerifyRecordDao.insertList(list);
    return count;
  }

  @Override
  public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
    Product record = new Product();
    record.setPublishStatus(publishStatus);
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    return productMapper.updateByExampleSelective(record, example);
  }

  @Override
  public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
    Product record = new Product();
    record.setRecommandStatus(recommendStatus);
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    return productMapper.updateByExampleSelective(record, example);
  }

  @Override
  public int updateNewStatus(List<Long> ids, Integer newStatus) {
    Product record = new Product();
    record.setNewStatus(newStatus);
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    return productMapper.updateByExampleSelective(record, example);
  }

  @Override
  public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
    Product record = new Product();
    record.setDeleteStatus(deleteStatus);
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    return productMapper.updateByExampleSelective(record, example);
  }

  @Override
  public List<Product> list(String keyword) {
    ProductExample productExample = new ProductExample();
    ProductExample.Criteria criteria = productExample.createCriteria();
    criteria.andDeleteStatusEqualTo(0);
    if (!StringUtils.isEmpty(keyword)) {
      criteria.andNameLike("%" + keyword + "%");
      productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
    }
    return productMapper.selectByExample(productExample);
  }

  /**
   * 建立和插入关系表操作
   *
   * @param dao 可以操作的dao
   * @param dataList 要插入的数据
   * @param productId 建立关系的id
   */
  private void relateAndInsertList(Object dao, List dataList, Long productId) {
    try {
      if (CollectionUtils.isEmpty(dataList)) return;
      for (Object item : dataList) {
        Method setId = item.getClass().getMethod("setId", Long.class);
        setId.invoke(item, (Long) null);
        Method setProductId = item.getClass().getMethod("setProductId", Long.class);
        setProductId.invoke(item, productId);
      }
      Method insertList = dao.getClass().getMethod("insertList", List.class);
      insertList.invoke(dao, dataList);
    } catch (Exception e) {
      LOGGER.warn("创建产品出错:{}", e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }
}
