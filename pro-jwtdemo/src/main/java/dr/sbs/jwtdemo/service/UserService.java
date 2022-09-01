package dr.sbs.jwtdemo.service;

import dr.sbs.jwtdemo.dto.UserQueryParam;
import dr.sbs.mbg.model.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
  User getRecord(long id);

  @Transactional
  int update(long id, User user);

  List<User> list(UserQueryParam userQueryParam, Integer pageSize, Integer pageNum);
}
