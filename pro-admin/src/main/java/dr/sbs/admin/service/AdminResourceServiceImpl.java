package dr.sbs.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import dr.sbs.mbg.mapper.AdminResourceMapper;
import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminResourceExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台资源管理Service实现类 */
@Service
public class AdminResourceServiceImpl implements AdminResourceService {
  @Autowired private AdminResourceMapper resourceMapper;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public int create(AdminResource adminResource) {
    return resourceMapper.insertSelective(adminResource);
  }

  @Override
  public int update(Long id, AdminResource adminResource) {
    adminResource.setId(id);
    int count = resourceMapper.updateByPrimaryKeySelective(adminResource);
    userCacheService.delResourceListByResource(id);
    return count;
  }

  @Override
  public AdminResource getItem(Long id) {
    return resourceMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(Long id) {
    AdminResource adminResource = new AdminResource();
    adminResource.setId(id);
    adminResource.setStatus(-1);
    int count = resourceMapper.updateByPrimaryKeySelective(adminResource);
    userCacheService.delResourceListByResource(id);
    return count;
  }

  @Override
  public List<AdminResource> list(
      Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminResourceExample example = new AdminResourceExample();
    AdminResourceExample.Criteria criteria = example.createCriteria();
    criteria.andStatusEqualTo(1);
    if (categoryId != null) {
      criteria.andCategoryIdEqualTo(categoryId);
    }
    if (StrUtil.isNotEmpty(nameKeyword)) {
      criteria.andNameLike('%' + nameKeyword + '%');
    }
    if (StrUtil.isNotEmpty(urlKeyword)) {
      criteria.andUrlLike('%' + urlKeyword + '%');
    }
    return resourceMapper.selectByExample(example);
  }

  @Override
  public List<AdminResource> listAll() {
    AdminResourceExample example = new AdminResourceExample();
    AdminResourceExample.Criteria criteria = example.createCriteria();
    criteria.andStatusEqualTo(1);
    return resourceMapper.selectByExample(example);
  }
}
