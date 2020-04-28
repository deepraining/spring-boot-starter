package senntyou.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dao.ProductAttributeCategoryDao;
import senntyou.sbs.admin.dto.ProductAttributeCategoryItem;
import senntyou.sbs.admin.service.ProductAttributeCategoryService;
import senntyou.sbs.mbg.mapper.ProductAttributeCategoryMapper;
import senntyou.sbs.mbg.model.ProductAttributeCategory;
import senntyou.sbs.mbg.model.ProductAttributeCategoryExample;

/** ProductAttributeCategoryService实现类 */
@Service
public class ProductAttributeCategoryServiceImpl implements ProductAttributeCategoryService {
  @Autowired private ProductAttributeCategoryMapper productAttributeCategoryMapper;
  @Autowired private ProductAttributeCategoryDao productAttributeCategoryDao;

  @Override
  public int create(String name) {
    ProductAttributeCategory productAttributeCategory = new ProductAttributeCategory();
    productAttributeCategory.setName(name);
    return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
  }

  @Override
  public int update(Long id, String name) {
    ProductAttributeCategory productAttributeCategory = new ProductAttributeCategory();
    productAttributeCategory.setName(name);
    productAttributeCategory.setId(id);
    return productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
  }

  @Override
  public int delete(Long id) {
    return productAttributeCategoryMapper.deleteByPrimaryKey(id);
  }

  @Override
  public ProductAttributeCategory getItem(Long id) {
    return productAttributeCategoryMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<ProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return productAttributeCategoryMapper.selectByExample(new ProductAttributeCategoryExample());
  }

  @Override
  public List<ProductAttributeCategoryItem> getListWithAttr() {
    return productAttributeCategoryDao.getListWithAttr();
  }
}
