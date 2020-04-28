package senntyou.sbs.admin.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.admin.dto.ProductAttrInfo;
import senntyou.sbs.admin.dto.ProductAttributeParam;
import senntyou.sbs.mbg.model.ProductAttribute;

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
