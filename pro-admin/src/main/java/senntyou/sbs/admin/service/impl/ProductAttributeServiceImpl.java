package senntyou.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dao.ProductAttributeDao;
import senntyou.sbs.admin.dto.ProductAttrInfo;
import senntyou.sbs.admin.dto.ProductAttributeParam;
import senntyou.sbs.admin.service.ProductAttributeService;
import senntyou.sbs.mbg.mapper.ProductAttributeCategoryMapper;
import senntyou.sbs.mbg.mapper.ProductAttributeMapper;
import senntyou.sbs.mbg.model.ProductAttribute;
import senntyou.sbs.mbg.model.ProductAttributeCategory;
import senntyou.sbs.mbg.model.ProductAttributeExample;

/** 商品属性Service实现类 Created by macro on 2018/4/26. */
@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
  @Autowired private ProductAttributeMapper productAttributeMapper;
  @Autowired private ProductAttributeCategoryMapper productAttributeCategoryMapper;
  @Autowired private ProductAttributeDao productAttributeDao;

  @Override
  public List<ProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    ProductAttributeExample example = new ProductAttributeExample();
    example.setOrderByClause("sort desc");
    example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
    return productAttributeMapper.selectByExample(example);
  }

  @Override
  public int create(ProductAttributeParam pmsProductAttributeParam) {
    ProductAttribute pmsProductAttribute = new ProductAttribute();
    BeanUtils.copyProperties(pmsProductAttributeParam, pmsProductAttribute);
    int count = productAttributeMapper.insertSelective(pmsProductAttribute);
    // 新增商品属性以后需要更新商品属性分类数量
    ProductAttributeCategory pmsProductAttributeCategory =
        productAttributeCategoryMapper.selectByPrimaryKey(
            pmsProductAttribute.getProductAttributeCategoryId());
    if (pmsProductAttribute.getType() == 0) {
      pmsProductAttributeCategory.setAttributeCount(
          pmsProductAttributeCategory.getAttributeCount() + 1);
    } else if (pmsProductAttribute.getType() == 1) {
      pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
    }
    productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
    return count;
  }

  @Override
  public int update(Long id, ProductAttributeParam productAttributeParam) {
    ProductAttribute pmsProductAttribute = new ProductAttribute();
    pmsProductAttribute.setId(id);
    BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
    return productAttributeMapper.updateByPrimaryKeySelective(pmsProductAttribute);
  }

  @Override
  public ProductAttribute getItem(Long id) {
    return productAttributeMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(List<Long> ids) {
    // 获取分类
    ProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
    Integer type = pmsProductAttribute.getType();
    ProductAttributeCategory pmsProductAttributeCategory =
        productAttributeCategoryMapper.selectByPrimaryKey(
            pmsProductAttribute.getProductAttributeCategoryId());
    ProductAttributeExample example = new ProductAttributeExample();
    example.createCriteria().andIdIn(ids);
    int count = productAttributeMapper.deleteByExample(example);
    // 删除完成后修改数量
    if (type == 0) {
      if (pmsProductAttributeCategory.getAttributeCount() >= count) {
        pmsProductAttributeCategory.setAttributeCount(
            pmsProductAttributeCategory.getAttributeCount() - count);
      } else {
        pmsProductAttributeCategory.setAttributeCount(0);
      }
    } else if (type == 1) {
      if (pmsProductAttributeCategory.getParamCount() >= count) {
        pmsProductAttributeCategory.setParamCount(
            pmsProductAttributeCategory.getParamCount() - count);
      } else {
        pmsProductAttributeCategory.setParamCount(0);
      }
    }
    productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
    return count;
  }

  @Override
  public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
    return productAttributeDao.getProductAttrInfo(productCategoryId);
  }
}
