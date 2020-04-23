package senntyou.sbs.admin.dto;

import java.util.List;
import senntyou.sbs.mbg.model.ProductCategory;

public class ProductCategoryWithChildrenItem extends ProductCategory {
  private List<ProductCategory> children;

  public List<ProductCategory> getChildren() {
    return children;
  }

  public void setChildren(List<ProductCategory> children) {
    this.children = children;
  }
}
