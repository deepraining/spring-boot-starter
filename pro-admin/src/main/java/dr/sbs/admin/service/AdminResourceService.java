package dr.sbs.admin.service;

import dr.sbs.mbg.model.AdminResource;
import java.util.List;

/** 后台资源管理Service */
public interface AdminResourceService {
  /** 添加资源 */
  int create(AdminResource adminResource);

  /** 修改资源 */
  int update(Long id, AdminResource adminResource);

  /** 获取资源详情 */
  AdminResource getItem(Long id);

  /** 删除资源 */
  int delete(Long id);

  /** 分页查询资源 */
  List<AdminResource> list(
      Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

  /** 查询全部资源 */
  List<AdminResource> listAll();
}
