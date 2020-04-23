package senntyou.sbs.admin.dto;

import java.util.List;
import senntyou.sbs.mbg.model.ProductAttribute;
import senntyou.sbs.mbg.model.ProductAttributeCategory;

/** 包含有分类下属性的dto */
public class ProductAttributeCategoryItem extends ProductAttributeCategory {
  private List<ProductAttribute> productAttributeList;

  public List<ProductAttribute> getProductAttributeList() {
    return productAttributeList;
  }

  public void setProductAttributeList(List<ProductAttribute> productAttributeList) {
    this.productAttributeList = productAttributeList;
  }
}
