package senntyou.sbs.jwtdemo.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.jwtdemo.dto.UserQueryParam;
import senntyou.sbs.mbg.model.User;

public interface UserService {
  User getRecord(long id);

  @Transactional
  int update(long id, User user);

  List<User> list(UserQueryParam userQueryParam, Integer pageSize, Integer pageNum);
}
