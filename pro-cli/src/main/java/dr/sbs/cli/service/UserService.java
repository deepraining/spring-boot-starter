package dr.sbs.cli.service;

import dr.sbs.mbg.model.FrontUser;
import java.util.List;

public interface UserService {
  List<FrontUser> list(Integer pageSize, Integer pageNum);
}
