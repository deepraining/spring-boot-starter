package dr.sbs.admin.dto;

import dr.sbs.mbg.model.ProductCategory;
import java.util.List;

public class ProductCategoryWithChildrenItem extends ProductCategory {
  private List<ProductCategory> children;

  public List<ProductCategory> getChildren() {
    return children;
  }

  public void setChildren(List<ProductCategory> children) {
    this.children = children;
  }
}
