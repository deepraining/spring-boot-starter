package senntyou.sbs.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.service.AdminResourceCategoryService;
import senntyou.sbs.mbg.mapper.AdminResourceCategoryMapper;
import senntyou.sbs.mbg.model.AdminResourceCategory;
import senntyou.sbs.mbg.model.AdminResourceCategoryExample;

/** 后台资源分类管理Service实现类 */
@Service
public class AdminResourceCategoryServiceImpl implements AdminResourceCategoryService {
  @Autowired private AdminResourceCategoryMapper resourceCategoryMapper;

  @Override
  public List<AdminResourceCategory> listAll() {
    AdminResourceCategoryExample example = new AdminResourceCategoryExample();
    example.setOrderByClause("sort desc");
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
    return resourceCategoryMapper.deleteByPrimaryKey(id);
  }
}
