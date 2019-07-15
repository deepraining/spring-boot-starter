package senntyou.sbs.demo.service;

import org.springframework.transaction.annotation.Transactional;
import senntyou.sbs.common.CommonResult;
import senntyou.sbs.gen.model.User;

public interface UserService {
  /** Get user by username */
  User getByUsername(String username);

  /** Get user by uuid */
  User getByUuid(String uuid);

  /** Sign up */
  @Transactional
  CommonResult register(String username, String password, String email);

  /** Update password */
  @Transactional
  CommonResult updatePassword(String email, String password);

  /** Get current user information */
  User getCurrentUser();
}
