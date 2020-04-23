package senntyou.sbs.admin.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import senntyou.sbs.admin.security.component.DynamicSecurityService;
import senntyou.sbs.admin.security.config.SecurityConfig;
import senntyou.sbs.admin.service.AdminResourceService;
import senntyou.sbs.admin.service.AdminUserService;
import senntyou.sbs.mbg.model.AdminResource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

  @Autowired private AdminUserService adminService;
  @Autowired private AdminResourceService resourceService;

  @Bean
  public UserDetailsService userDetailsService() {
    // 获取登录用户信息
    return username -> adminService.loadUserByUsername(username);
  }

  @Bean
  public DynamicSecurityService dynamicSecurityService() {
    return new DynamicSecurityService() {
      @Override
      public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<AdminResource> resourceList = resourceService.listAll();
        for (AdminResource resource : resourceList) {
          map.put(
              resource.getUrl(),
              new org.springframework.security.access.SecurityConfig(
                  resource.getId() + ":" + resource.getName()));
        }
        return map;
      }
    };
  }
}
