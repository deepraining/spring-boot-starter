package dr.sbs.wx.config;

import dr.sbs.mbg.model.WxUser;
import dr.sbs.wx.bo.UserInfo;
import dr.sbs.wx.component.JwtAuthenticationTokenFilter;
import dr.sbs.wx.component.RestAuthenticationEntryPoint;
import dr.sbs.wx.component.RestfulAccessDeniedHandler;
import dr.sbs.wx.service.UserService;
import dr.sbs.wx.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private UserService userService;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
        httpSecurity.authorizeRequests();
    // 不需要保护的资源路径允许访问
    for (String url : ignoreUrlsConfig().getUrls()) {
      registry.antMatchers(url).permitAll();
    }
    // 允许跨域请求的OPTIONS请求
    registry.antMatchers(HttpMethod.OPTIONS).permitAll();
    // 任何请求需要身份认证
    registry
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        // 关闭跨站请求防护及不使用session
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 自定义权限拒绝处理类
        .and()
        .exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler())
        .authenticationEntryPoint(restAuthenticationEntryPoint())
        // 自定义权限拦截器JWT过滤器
        .and()
        .addFilterBefore(
            jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    // Disable cache
    httpSecurity.headers().cacheControl();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

  @Bean
  public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
    return new RestfulAccessDeniedHandler();
  }

  @Bean
  public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new RestAuthenticationEntryPoint();
  }

  @Bean
  public IgnoreUrlsConfig ignoreUrlsConfig() {
    return new IgnoreUrlsConfig();
  }

  @Bean
  public JwtTokenUtil jwtTokenUtil() {
    return new JwtTokenUtil();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    // Get logged-in user information
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String unionId) throws UsernameNotFoundException {
        WxUser user = userService.getByUnionId(unionId);
        if (user != null) {
          return new UserInfo(user);
        }
        throw new UsernameNotFoundException("unionId is not correct");
      }
    };
  }
}
