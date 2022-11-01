package dr.sbs.cli.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.mbg.mapper.FrontUserMapper;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.mbg.model.FrontUserExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private FrontUserMapper userMapper;

  @Override
  public List<FrontUser> list(Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    FrontUserExample example = new FrontUserExample();
    example.setOrderByClause("id desc");
    FrontUserExample.Criteria criteria = example.createCriteria();

    criteria.andStatusEqualTo((byte) 1);
    return userMapper.selectByExample(example);
  }
}
