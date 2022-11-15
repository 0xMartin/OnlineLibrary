package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.UserRepository;

import java.util.Optional;

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
    final Optional<User> user = users.findById(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException(username);
    }

    User u = user.get();
    UserDetails uDetails = org.springframework.security.core.userdetails.User
      .withUsername(u.getUsername())
      .password(u.getPassword())
      .authorities("ROLE_" + u.getRole().getName().toString())
      .build();

    return uDetails;
  }
}
