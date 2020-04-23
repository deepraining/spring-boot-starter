package senntyou.sbs.admin.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import senntyou.sbs.mbg.model.AdminPermission;

/** 后台权限节点封装 */
public class AdminPermissionNode extends AdminPermission {
  @Getter @Setter private List<AdminPermissionNode> children;
}
