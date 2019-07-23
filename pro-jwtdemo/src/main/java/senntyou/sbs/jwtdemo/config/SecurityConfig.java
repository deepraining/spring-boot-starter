package senntyou.sbs.jwtdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import senntyou.sbs.jwtdemo.bo.UserInfo;
import senntyou.sbs.jwtdemo.component.JwtAuthenticationTokenFilter;
import senntyou.sbs.jwtdemo.component.RestAuthenticationEntryPoint;
import senntyou.sbs.jwtdemo.component.RestfulAccessDeniedHandler;
import senntyou.sbs.jwtdemo.service.JwtUserService;
import senntyou.sbs.mbg.model.JwtUser;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired private JwtUserService userService;
  @Autowired private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  @Autowired private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf() // JWT does not need csrf
        .disable()
        .sessionManagement() // JWT does not need session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-resources/**",
            "/v2/api-docs/**")
        .permitAll()
        .antMatchers("/account/login", "/account/register")
        .permitAll()
        // Every cross origin request will make a OPTIONS request before its real request
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .anyRequest()
        .authenticated();
    // Disable cache
    httpSecurity.headers().cacheControl();
    // Add JWT filter
    httpSecurity.addFilterBefore(
        jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    // Add custom exception handler
    httpSecurity
        .exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint);
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
  public UserDetailsService userDetailsService() {
    // Get logged-in user information
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser user = userService.getByUsername(username);
        if (user != null) {
          return new UserInfo(user);
        }
        throw new UsernameNotFoundException("Username or password is not correct");
      }
    };
  }
}
