package dr.sbs.admin.dto;

import dr.sbs.mbg.model.AdminPermission;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** 后台权限节点封装 */
public class AdminPermissionNode extends AdminPermission {
  @Getter @Setter private List<AdminPermissionNode> children;
}
