package senntyou.sbs.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.service.AdminResourceService;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.mbg.mapper.AdminResourceMapper;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminResourceExample;

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
    int count = resourceMapper.deleteByPrimaryKey(id);
    userCacheService.delResourceListByResource(id);
    return count;
  }

  @Override
  public List<AdminResource> list(
      Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminResourceExample example = new AdminResourceExample();
    AdminResourceExample.Criteria criteria = example.createCriteria();
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
    return resourceMapper.selectByExample(new AdminResourceExample());
  }
}
