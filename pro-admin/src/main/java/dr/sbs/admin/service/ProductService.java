package dr.sbs.admin.service;

import dr.sbs.admin.dto.ProductParam;
import dr.sbs.admin.dto.ProductQueryParam;
import dr.sbs.admin.dto.ProductResult;
import dr.sbs.mbg.model.Product;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 商品管理Service */
public interface ProductService {
  /** 创建商品 */
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
  int create(ProductParam productParam);

  /** 根据商品编号获取更新信息 */
  ProductResult getUpdateInfo(Long id);

  /** 更新商品 */
  @Transactional
  int update(Long id, ProductParam productParam);

  /** 分页查询商品 */
  List<Product> list(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

  /**
   * 批量修改审核状态
   *
   * @param ids 产品id
   * @param verifyStatus 审核状态
   * @param detail 审核详情
   */
  @Transactional
  int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

  /** 批量修改商品上架状态 */
  int updatePublishStatus(List<Long> ids, Integer publishStatus);

  /** 批量修改商品推荐状态 */
  int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

  /** 批量修改新品状态 */
  int updateNewStatus(List<Long> ids, Integer newStatus);

  /** 批量删除商品 */
  int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

  /** 根据商品名称或者货号模糊查询 */
  List<Product> list(String keyword);
}
