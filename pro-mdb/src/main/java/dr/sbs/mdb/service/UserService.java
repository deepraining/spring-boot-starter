package dr.sbs.mdb.service;

import dr.sbs.mbg.model.FrontUser;
import java.util.List;

public interface UserService {
  // 所有用户列表
  List<FrontUser> listAll();

  // 添加用户
  int create(FrontUser frontUser);

  // 更新用户
  int update(Long id, FrontUser frontUser);

  // 删除用户
  int delete(Long id);
}
