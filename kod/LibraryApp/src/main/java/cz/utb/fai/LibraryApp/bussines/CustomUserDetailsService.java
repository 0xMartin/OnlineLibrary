package cz.utb.fai.LibraryApp.bussines;

import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User Deatil servis pro ziskani uzivatelskych dat z databaze.
 * K datum uzivatele se pristupuje pomoci jeho uzivatelskeho jmena.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  protected UserRepository users;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    final User user = users.findItemByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    UserDetails uDetails = org.springframework.security.core.userdetails.User
      .withUsername(user.getUsername())
      .password(user.getPassword())
      .authorities("ROLE_" + user.getRole().getName().toString())
      .build();

    return uDetails;
  }
}
