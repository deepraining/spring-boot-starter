package dr.sbs.admin.service;

import dr.sbs.admin.dto.ProductAttrInfo;
import dr.sbs.admin.dto.ProductAttributeParam;
import dr.sbs.mbg.model.ProductAttribute;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/** 商品属性Service */
public interface ProductAttributeService {
  /**
   * 根据分类分页获取商品属性
   *
   * @param cid 分类id
   * @param type 0->属性；2->参数
   */
  List<ProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

  /** 添加商品属性 */
  @Transactional
  int create(ProductAttributeParam productAttributeParam);

  /** 修改商品属性 */
  int update(Long id, ProductAttributeParam productAttributeParam);

  /** 获取单个商品属性信息 */
  ProductAttribute getItem(Long id);

  @Transactional
  int delete(List<Long> ids);

  List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
