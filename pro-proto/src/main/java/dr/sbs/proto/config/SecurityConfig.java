package dr.sbs.proto.config;

import dr.sbs.mbg.model.FrontUser;
import dr.sbs.proto.bo.UserInfo;
import dr.sbs.proto.component.GoAccessDeniedHandler;
import dr.sbs.proto.component.GoAuthenticationEntryPoint;
import dr.sbs.proto.component.GoAuthenticationFailureHandler;
import dr.sbs.proto.component.GoAuthenticationSuccessHandler;
import dr.sbs.proto.component.GoLogoutSuccessHandler;
import dr.sbs.proto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private UserService userService;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests()
        .antMatchers("/user", "/api/user/**")
        .authenticated()
        .anyRequest()
        .permitAll()
        // Every cross origin request will make a OPTIONS request before its real request
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new GoAccessDeniedHandler())
        .authenticationEntryPoint(new GoAuthenticationEntryPoint())
        .and()
        .formLogin()
        .loginPage("/account/login")
        .loginProcessingUrl("/api/account/login")
        .successHandler(new GoAuthenticationSuccessHandler())
        .failureHandler(new GoAuthenticationFailureHandler())
        .and()
        .logout()
        .logoutUrl("/api/account/logout")
        .logoutSuccessHandler(new GoLogoutSuccessHandler())
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .and()
        .csrf()
        .disable();
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
  public UserDetailsService userDetailsService() {
    // Get logged-in user information
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FrontUser user = userService.getByUsername(username);
        if (user != null) {
          return new UserInfo(user);
        }
        throw new UsernameNotFoundException("Username or password is not correct");
      }
    };
  }
}
