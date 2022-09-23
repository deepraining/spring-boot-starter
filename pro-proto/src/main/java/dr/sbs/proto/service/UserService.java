package dr.sbs.proto.service;

import dr.sbs.mbg.model.FrontUser;
import dr.sbs.proto.dto.UpdatePasswordParam;
import dr.sbs.proto.dto.UserCreateParam;

public interface UserService {
  /** Get user by username */
  FrontUser getByUsername(String username);

  /** Get user by id */
  FrontUser getById(Long id);

  /** Sign up */
  FrontUser register(UserCreateParam userCreateParam);

  /** Update password */
  Integer updatePassword(UpdatePasswordParam updatePasswordParam);

  /** Get current user information */
  FrontUser getCurrentUser();
}
