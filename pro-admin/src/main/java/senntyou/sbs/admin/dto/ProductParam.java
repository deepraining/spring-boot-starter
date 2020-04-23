package senntyou.sbs.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import senntyou.sbs.mbg.model.Product;
import senntyou.sbs.mbg.model.ProductAttributeValue;

/** 创建和修改商品时使用的参数 */
public class ProductParam extends Product {
  @ApiModelProperty("商品参数及自定义规格属性")
  private List<ProductAttributeValue> productAttributeValueList;

  public List<ProductAttributeValue> getProductAttributeValueList() {
    return productAttributeValueList;
  }

  public void setProductAttributeValueList(List<ProductAttributeValue> productAttributeValueList) {
    this.productAttributeValueList = productAttributeValueList;
  }
}
