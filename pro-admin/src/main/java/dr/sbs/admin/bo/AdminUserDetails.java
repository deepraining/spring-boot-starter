package dr.sbs.admin.bo;

import dr.sbs.mbg.model.AdminResource;
import dr.sbs.mbg.model.AdminUser;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** SpringSecurity 需要的用户详情 */
public class AdminUserDetails implements UserDetails {
  private AdminUser adminUser;
  private List<AdminResource> resourceList;

  public AdminUserDetails(AdminUser adminUser, List<AdminResource> resourceList) {
    this.adminUser = adminUser;
    this.resourceList = resourceList;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 返回当前用户的资源对象
    return resourceList.stream()
        .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return adminUser.getPassword();
  }

  @Override
  public String getUsername() {
    return adminUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return adminUser.getStatus().equals(1);
  }
}
