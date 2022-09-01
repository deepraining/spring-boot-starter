package dr.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import dr.sbs.admin.dao.ProductAttributeDao;
import dr.sbs.admin.dto.ProductAttrInfo;
import dr.sbs.admin.dto.ProductAttributeParam;
import dr.sbs.admin.service.ProductAttributeService;
import dr.sbs.mbg.mapper.ProductAttributeCategoryMapper;
import dr.sbs.mbg.mapper.ProductAttributeMapper;
import dr.sbs.mbg.model.ProductAttribute;
import dr.sbs.mbg.model.ProductAttributeCategory;
import dr.sbs.mbg.model.ProductAttributeExample;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 商品属性Service实现类 */
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
  public int create(ProductAttributeParam productAttributeParam) {
    ProductAttribute productAttribute = new ProductAttribute();
    BeanUtils.copyProperties(productAttributeParam, productAttribute);
    int count = productAttributeMapper.insertSelective(productAttribute);
    // 新增商品属性以后需要更新商品属性分类数量
    ProductAttributeCategory productAttributeCategory =
        productAttributeCategoryMapper.selectByPrimaryKey(
            productAttribute.getProductAttributeCategoryId());
    if (productAttribute.getType() == 0) {
      productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
    } else if (productAttribute.getType() == 1) {
      productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
    }
    productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
    return count;
  }

  @Override
  public int update(Long id, ProductAttributeParam productAttributeParam) {
    ProductAttribute productAttribute = new ProductAttribute();
    productAttribute.setId(id);
    BeanUtils.copyProperties(productAttributeParam, productAttribute);
    return productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
  }

  @Override
  public ProductAttribute getItem(Long id) {
    return productAttributeMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(List<Long> ids) {
    // 获取分类
    ProductAttribute productAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
    Integer type = productAttribute.getType();
    ProductAttributeCategory productAttributeCategory =
        productAttributeCategoryMapper.selectByPrimaryKey(
            productAttribute.getProductAttributeCategoryId());
    ProductAttributeExample example = new ProductAttributeExample();
    example.createCriteria().andIdIn(ids);
    int count = productAttributeMapper.deleteByExample(example);
    // 删除完成后修改数量
    if (type == 0) {
      if (productAttributeCategory.getAttributeCount() >= count) {
        productAttributeCategory.setAttributeCount(
            productAttributeCategory.getAttributeCount() - count);
      } else {
        productAttributeCategory.setAttributeCount(0);
      }
    } else if (type == 1) {
      if (productAttributeCategory.getParamCount() >= count) {
        productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() - count);
      } else {
        productAttributeCategory.setParamCount(0);
      }
    }
    productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
    return count;
  }

  @Override
  public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
    return productAttributeDao.getProductAttrInfo(productCategoryId);
  }
}
