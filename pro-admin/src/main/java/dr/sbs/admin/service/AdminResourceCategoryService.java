package dr.sbs.admin.service;

import dr.sbs.mbg.model.AdminResourceCategory;
import java.util.List;

/** 后台资源分类管理Service */
public interface AdminResourceCategoryService {

  /** 获取所有资源分类 */
  List<AdminResourceCategory> listAll();

  /** 创建资源分类 */
  int create(AdminResourceCategory adminResourceCategory);

  /** 修改资源分类 */
  int update(Long id, AdminResourceCategory adminResourceCategory);

  /** 删除资源分类 */
  int delete(Long id);
}
