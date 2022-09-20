package dr.sbs.mdb.service;

import dr.sbs.common.util.UuidUtil;
import dr.sbs.mbg.mapper.FrontUserMapper;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.mbg.model.FrontUserExample;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  @Autowired private FrontUserMapper frontUserMapper;

  @Override
  public List<FrontUser> listAll() {
    return frontUserMapper.selectByExample(new FrontUserExample());
  }

  @Override
  public int create(FrontUser frontUser) {
    frontUser.setId(UuidUtil.nextId());
    if (frontUser.getPassword() == null) frontUser.setPassword("1234567890");
    return frontUserMapper.insertSelective(frontUser);
  }

  @Override
  public int update(Long id, FrontUser frontUser) {
    frontUser.setId(id);
    return frontUserMapper.updateByPrimaryKeySelective(frontUser);
  }

  @Override
  public int delete(Long id) {
    return frontUserMapper.deleteByPrimaryKey(id);
  }
}
