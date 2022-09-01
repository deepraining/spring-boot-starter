package dr.sbs.admin.service;

import dr.sbs.admin.dto.ProductCategoryParam;
import dr.sbs.admin.dto.ProductCategoryWithChildrenItem;
import dr.sbs.mbg.model.ProductCategory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/** 商品分类Service */
public interface ProductCategoryService {
  /** 创建商品分类 */
  @Transactional
  int create(ProductCategoryParam productCategoryParam);

  /** 修改商品分类 */
  @Transactional
  int update(Long id, ProductCategoryParam productCategoryParam);

  /** 分页获取商品分类 */
  List<ProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

  /** 删除商品分类 */
  int delete(Long id);

  /** 根据ID获取商品分类 */
  ProductCategory getItem(Long id);

  /** 批量修改导航状态 */
  int updateNavStatus(List<Long> ids, Integer navStatus);

  /** 批量修改显示状态 */
  int updateShowStatus(List<Long> ids, Integer showStatus);

  /** 以层级形式获取商品分类 */
  List<ProductCategoryWithChildrenItem> listWithChildren();
}
