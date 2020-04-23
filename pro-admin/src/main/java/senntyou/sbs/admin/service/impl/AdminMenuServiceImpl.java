package senntyou.sbs.admin.service.impl;

import com.github.pagehelper.PageHelper;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senntyou.sbs.admin.dto.AdminMenuNode;
import senntyou.sbs.admin.service.AdminMenuService;
import senntyou.sbs.mbg.mapper.AdminMenuMapper;
import senntyou.sbs.mbg.model.*;

/** 后台菜单管理Service实现类 Created by macro on 2020/2/2. */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {
  @Autowired private AdminMenuMapper menuMapper;

  @Override
  public int create(AdminMenu umsMenu) {
    umsMenu.setCreateTime(new Date());
    updateLevel(umsMenu);
    return menuMapper.insert(umsMenu);
  }

  /** 修改菜单层级 */
  private void updateLevel(AdminMenu umsMenu) {
    if (umsMenu.getParentId() == 0) {
      // 没有父菜单时为一级菜单
      umsMenu.setLevel(0);
    } else {
      // 有父菜单时选择根据父菜单level设置
      AdminMenu parentMenu = menuMapper.selectByPrimaryKey(umsMenu.getParentId());
      if (parentMenu != null) {
        umsMenu.setLevel(parentMenu.getLevel() + 1);
      } else {
        umsMenu.setLevel(0);
      }
    }
  }

  @Override
  public int update(Long id, AdminMenu umsMenu) {
    umsMenu.setId(id);
    updateLevel(umsMenu);
    return menuMapper.updateByPrimaryKeySelective(umsMenu);
  }

  @Override
  public AdminMenu getItem(Long id) {
    return menuMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(Long id) {
    return menuMapper.deleteByPrimaryKey(id);
  }

  @Override
  public List<AdminMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminMenuExample example = new AdminMenuExample();
    example.setOrderByClause("sort desc");
    example.createCriteria().andParentIdEqualTo(parentId);
    return menuMapper.selectByExample(example);
  }

  @Override
  public List<AdminMenuNode> treeList() {
    List<AdminMenu> menuList = menuMapper.selectByExample(new AdminMenuExample());
    List<AdminMenuNode> result =
        menuList.stream()
            .filter(menu -> menu.getParentId().equals(0L))
            .map(menu -> covertMenuNode(menu, menuList))
            .collect(Collectors.toList());
    return result;
  }

  @Override
  public int updateHidden(Long id, Integer hidden) {
    AdminMenu umsMenu = new AdminMenu();
    umsMenu.setId(id);
    umsMenu.setHidden(hidden);
    return menuMapper.updateByPrimaryKeySelective(umsMenu);
  }

  /** 将AdminMenu转化为AdminMenuNode并设置children属性 */
  private AdminMenuNode covertMenuNode(AdminMenu menu, List<AdminMenu> menuList) {
    AdminMenuNode node = new AdminMenuNode();
    BeanUtils.copyProperties(menu, node);
    List<AdminMenuNode> children =
        menuList.stream()
            .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
            .map(subMenu -> covertMenuNode(subMenu, menuList))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }
}
