package cz.utb.fai.LibraryApp;

import cz.utb.fai.LibraryApp.enums.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

  @Bean
  public static PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
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
      .loginProcessingUrl("/auth/process-login")
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
    return http.build();
  }
}
