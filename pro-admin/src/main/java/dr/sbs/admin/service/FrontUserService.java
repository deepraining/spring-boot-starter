package dr.sbs.admin.service;

import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.mbg.model.FrontUser;
import java.util.List;

/** 前端用户管理Service */
public interface FrontUserService {
  /** 获取前端用户列表 */
  List<FrontUser> list(String searchKey, Integer pageSize, Integer pageNum);

  /** 创建前端用户 */
  int create(FrontUserCreateParam frontUserCreateParam);

  /** 修改前端用户 */
  int update(Long id, FrontUserCreateParam frontUserCreateParam);

  /** 删除前端用户 */
  int delete(Long id);
}
