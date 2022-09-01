package dr.sbs.demo.service;

import dr.sbs.common.CommonResult;
import dr.sbs.mbg.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
  /** Get user by username */
  User getByUsername(String username);

  /** Get user by id */
  User getById(long id);

  /** Sign up */
  @Transactional
  CommonResult register(String username, String password, String email);

  /** Update password */
  @Transactional
  CommonResult updatePassword(String username, String password);

  /** Get current user information */
  User getCurrentUser();
}
