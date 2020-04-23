package senntyou.sbs.admin.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import senntyou.sbs.mbg.model.AdminMenu;

/** 后台菜单节点封装 */
@Getter
@Setter
public class AdminMenuNode extends AdminMenu {
  private List<AdminMenuNode> children;
}
