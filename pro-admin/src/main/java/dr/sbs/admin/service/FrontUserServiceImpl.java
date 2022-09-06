package dr.sbs.admin.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.common.util.UuidUtil;
import dr.sbs.mbg.mapper.FrontUserMapper;
import dr.sbs.mbg.model.FrontUser;
import dr.sbs.mbg.model.FrontUserExample;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 后台前端用户管理Service实现类 */
@Service
public class FrontUserServiceImpl implements FrontUserService {
  @Autowired private FrontUserMapper frontUserMapper;

  @Override
  public List<FrontUser> list(String searchKey, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    FrontUserExample example = new FrontUserExample();
    example.setOrderByClause("id desc");
    FrontUserExample.Criteria criteria = example.createCriteria();
    criteria.andStatusEqualTo((byte) 1);
    if (!StringUtils.isEmpty(searchKey)) {
      criteria.andUsernameLike("%" + searchKey + "%");
    }
    return frontUserMapper.selectByExample(example);
  }

  @Override
  public int create(FrontUserCreateParam frontUserCreateParam) {
    FrontUser frontUser = new FrontUser();
    BeanUtils.copyProperties(frontUserCreateParam, frontUser);
    frontUser.setId(UuidUtil.nextId());
    return frontUserMapper.insertSelective(frontUser);
  }

  @Override
  public int update(Long id, FrontUserCreateParam frontUserCreateParam) {
    FrontUser frontUser = new FrontUser();
    BeanUtils.copyProperties(frontUserCreateParam, frontUser);
    frontUser.setId(id);
    // 不能更新用户名
    frontUser.setUsername(null);
    return frontUserMapper.updateByPrimaryKeySelective(frontUser);
  }

  @Override
  public int delete(Long id) {
    FrontUser frontUser = new FrontUser();
    frontUser.setId(id);
    frontUser.setStatus((byte) 0);
    return frontUserMapper.updateByPrimaryKeySelective(frontUser);
  }
}
