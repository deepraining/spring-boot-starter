package dr.sbs.admin.dto;

import dr.sbs.mbg.model.ProductAttribute;
import dr.sbs.mbg.model.ProductAttributeCategory;
import java.util.List;

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
