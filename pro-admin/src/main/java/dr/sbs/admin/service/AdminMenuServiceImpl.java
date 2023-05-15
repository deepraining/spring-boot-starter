package dr.sbs.admin.service;

import com.github.pagehelper.PageHelper;
import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.mbg.mapper.AdminMenuMapper;
import dr.sbs.mbg.model.AdminMenu;
import dr.sbs.mbg.model.AdminMenuExample;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台菜单管理Service实现类 */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {
  @Autowired private AdminMenuMapper menuMapper;

  @Override
  public int create(AdminMenu adminMenu) {
    updateLevel(adminMenu);
    return menuMapper.insertSelective(adminMenu);
  }

  /** 修改菜单层级 */
  private void updateLevel(AdminMenu adminMenu) {
    if (adminMenu.getParentId() == 0) {
      // 没有父菜单时为一级菜单
      adminMenu.setLevel(0);
    } else {
      // 有父菜单时选择根据父菜单level设置
      AdminMenu parentMenu = menuMapper.selectByPrimaryKey(adminMenu.getParentId());
      if (parentMenu != null) {
        adminMenu.setLevel(parentMenu.getLevel() + 1);
      } else {
        adminMenu.setLevel(0);
      }
    }
  }

  @Override
  public int update(Long id, AdminMenu adminMenu) {
    adminMenu.setId(id);
    updateLevel(adminMenu);
    return menuMapper.updateByPrimaryKeySelective(adminMenu);
  }

  @Override
  public AdminMenu getItem(Long id) {
    return menuMapper.selectByPrimaryKey(id);
  }

  @Override
  public int delete(Long id) {
    AdminMenu adminMenu = new AdminMenu();
    adminMenu.setId(id);
    adminMenu.setStatus(-1);

    return menuMapper.updateByPrimaryKeySelective(adminMenu);
  }

  @Override
  public List<AdminMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    AdminMenuExample example = new AdminMenuExample();
    example.setOrderByClause("sort desc");
    example.createCriteria().andParentIdEqualTo(parentId).andStatusEqualTo(1);
    return menuMapper.selectByExample(example);
  }

  @Override
  public List<AdminMenuNode> treeList() {
    AdminMenuExample example = new AdminMenuExample();
    example.setOrderByClause("sort desc");
    example.createCriteria().andStatusEqualTo(1);
    List<AdminMenu> menuList = menuMapper.selectByExample(example);
    List<AdminMenuNode> result =
        menuList.stream()
            .filter(menu -> menu.getParentId().equals(0L))
            .map(menu -> convertMenuNode(menu, menuList))
            .collect(Collectors.toList());
    return result;
  }

  @Override
  public int updateHidden(Long id, Integer hidden) {
    AdminMenu adminMenu = new AdminMenu();
    adminMenu.setId(id);
    adminMenu.setHidden(hidden);
    return menuMapper.updateByPrimaryKeySelective(adminMenu);
  }

  /** 将AdminMenu转化为AdminMenuNode并设置children属性 */
  private AdminMenuNode convertMenuNode(AdminMenu menu, List<AdminMenu> menuList) {
    AdminMenuNode node = new AdminMenuNode();
    BeanUtils.copyProperties(menu, node);
    List<AdminMenuNode> children =
        menuList.stream()
            .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
            .map(subMenu -> convertMenuNode(subMenu, menuList))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }
}
