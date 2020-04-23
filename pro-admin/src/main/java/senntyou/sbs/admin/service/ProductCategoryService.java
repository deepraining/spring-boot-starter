package senntyou.sbs.admin.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.admin.dto.ProductCategoryParam;
import senntyou.sbs.admin.dto.ProductCategoryWithChildrenItem;
import senntyou.sbs.mbg.model.ProductCategory;

/** 商品分类Service Created by macro on 2018/4/26. */
public interface ProductCategoryService {
  /** 创建商品分类 */
  @Transactional
  int create(ProductCategoryParam pmsProductCategoryParam);

  /** 修改商品分类 */
  @Transactional
  int update(Long id, ProductCategoryParam pmsProductCategoryParam);

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
