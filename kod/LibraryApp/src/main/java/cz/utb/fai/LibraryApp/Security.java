package cz.utb.fai.LibraryApp;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Security {

  @Bean
  public static PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
