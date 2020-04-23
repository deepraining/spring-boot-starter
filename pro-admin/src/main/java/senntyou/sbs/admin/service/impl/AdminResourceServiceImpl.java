package senntyou.sbs.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.service.AdminResourceService;
import senntyou.sbs.admin.service.AdminUserCacheService;
import senntyou.sbs.mbg.mapper.AdminResourceMapper;
import senntyou.sbs.mbg.model.AdminResource;
import senntyou.sbs.mbg.model.AdminResourceExample;

/** 后台资源管理Service实现类 Created by macro on 2020/2/2. */
@Service
public class AdminResourceServiceImpl implements AdminResourceService {
  @Autowired private AdminResourceMapper resourceMapper;
  @Autowired private AdminUserCacheService adminCacheService;

  @Override
  public int create(AdminResource umsResource) {
    umsResource.setCreateTime(new Date());
    return resourceMapper.insert(umsResource);
  }

  @Override
  public int update(Long id, AdminResource umsResource) {
    umsResource.setId(id);
    int count = resourceMapper.updateByPrimaryKeySelective(umsResource);
    adminCacheService.delResourceListByResource(id);
    return count;
  }

  @Override
  public AdminResource getItem(Long id) {
    return resourceMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(Long id) {
    int count = resourceMapper.deleteByPrimaryKey(id);
    adminCacheService.delResourceListByResource(id);
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
