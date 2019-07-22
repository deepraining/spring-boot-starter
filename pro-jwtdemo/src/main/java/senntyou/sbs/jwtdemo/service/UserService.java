package senntyou.sbs.jwtdemo.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.gen.model.User;
import senntyou.sbs.jwtdemo.dto.UserQueryParam;

public interface UserService {
  User getRecord(String uuid);

  @Transactional
  int update(String uuid, User user);

  List<User> list(UserQueryParam userQueryParam, Integer pageSize, Integer pageNum);
}
