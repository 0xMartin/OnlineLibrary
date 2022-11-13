package cz.utb.fai.LibraryApp;

import cz.utb.fai.LibraryApp.bussines.CustomUserDetailsService;
import cz.utb.fai.LibraryApp.enums.*;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfigurace zabezpeceni aplikace
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(
    SecurityConfig.class
  );

  /**
   * Vlastni user details service
   */
  @Resource
  private CustomUserDetailsService customUserDetailsService;

  /**
   * Encoder hesel pouzivany v cele aplikaci
   * @return PasswordEncoder
   */
  @Bean
  public static PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Poskytuje autentizaci uzivatelu
   * @return DaoAuthenticationProvider
   */
  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(SecurityConfig.encoder());
    authenticationProvider.setUserDetailsService(this.customUserDetailsService);
    return authenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/css/**")
      .permitAll()
      .antMatchers("/js/**")
      .permitAll()
      .antMatchers(AppRequestMapping.AUTH + "/**")
      .permitAll()
      .antMatchers(AppRequestMapping.HOME + "/**")
      .permitAll()
      .antMatchers(AppRequestMapping.PROFILE + "/**")
      .hasAnyRole(ERole.CUSTOMER.toString(), ERole.LIBRARIAN.toString())
      .antMatchers(AppRequestMapping.ADMIN + "/**")
      .hasAnyRole(ERole.LIBRARIAN.toString())
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .loginPage("/auth/login")
      .defaultSuccessUrl("/home")
      .failureUrl("/auth/login?error=true")
      .permitAll()
      .and()
      .logout()
      .logoutSuccessUrl("/login.html?logout=true")
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID")
      .permitAll()
      .and()
      .csrf()
      .disable();
    logger.info("HTTP FilterChain setup done");
    return http.build();
  }

  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
      AuthenticationManagerBuilder.class
    );
    authenticationManagerBuilder.authenticationProvider(this.authProvider());
    logger.info("Auth manager setup done");
    return authenticationManagerBuilder.build();
  }

}
