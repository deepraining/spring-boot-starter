package dr.sbs.admin.dto;

import dr.sbs.mbg.model.AdminMenu;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** 后台菜单节点封装 */
@Getter
@Setter
public class AdminMenuNode extends AdminMenu {
  private List<AdminMenuNode> children;
}
