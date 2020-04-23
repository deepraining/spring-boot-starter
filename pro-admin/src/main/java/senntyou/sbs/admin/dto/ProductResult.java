package senntyou.sbs.admin.dto;

/** 查询单个产品进行修改时返回的结果 */
public class ProductResult extends ProductParam {
  // 商品所选分类的父id
  private Long cateParentId;

  public Long getCateParentId() {
    return cateParentId;
  }

  public void setCateParentId(Long cateParentId) {
    this.cateParentId = cateParentId;
  }
}
