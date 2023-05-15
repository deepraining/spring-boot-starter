package dr.sbs.admin.service;

import dr.sbs.mbg.mapper.AdminResourceCategoryMapper;
import dr.sbs.mbg.model.AdminResourceCategory;
import dr.sbs.mbg.model.AdminResourceCategoryExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台资源分类管理Service实现类 */
@Service
public class AdminResourceCategoryServiceImpl implements AdminResourceCategoryService {
  @Autowired private AdminResourceCategoryMapper resourceCategoryMapper;

  @Override
  public List<AdminResourceCategory> listAll() {
    AdminResourceCategoryExample example = new AdminResourceCategoryExample();
    example.setOrderByClause("sort desc");
    example.createCriteria().andStatusEqualTo(1);
    return resourceCategoryMapper.selectByExample(example);
  }

  @Override
  public int create(AdminResourceCategory adminResourceCategory) {
    return resourceCategoryMapper.insertSelective(adminResourceCategory);
  }

  @Override
  public int update(Long id, AdminResourceCategory adminResourceCategory) {
    adminResourceCategory.setId(id);
    return resourceCategoryMapper.updateByPrimaryKeySelective(adminResourceCategory);
  }

  @Override
  public int delete(Long id) {
    AdminResourceCategory adminResourceCategory = new AdminResourceCategory();
    adminResourceCategory.setId(id);
    adminResourceCategory.setStatus(-1);
    return resourceCategoryMapper.updateByPrimaryKeySelective(adminResourceCategory);
  }
}
