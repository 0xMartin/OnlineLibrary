package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Servis pro spravu uzivatelu
 */
@Service
public class UserService {

  @Autowired
  protected UserRepository userRepository;

  /**
   * Navrati vsechny informace o aktualne prihlasenem uzivateli (profil uzivatele)
   * @return User
   */
  public User profile() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentPrincipalName = authentication.getName();
      return this.userRepository.findItemByUsername(currentPrincipalName);
    } else {
      return null;
    }
  }

  /**
   * V datatbazi najde uzivatele s konkretnim ID
   * @param id ID uzivatele
   * @return User
   */
  public User findUser(long id) {
    return null;
  }

  /**
   * Navrati vsechny uzivatle
   * @return List<User>
   */
  public List<User> users() {
    return null;
  }

  /**
   * V databazi vyhleda uzivatele, kteri splnuji specifikovane pozadavky vyhledavani
   * @param name Jmeno uzivatele
   * @param surname Prijmeni uzivatele
   * @param address Adresa uzivatele
   * @param personalID Rodne cislo uzivatle
   * @param sortedBy Razeni podle parametru (negativni cislo = bez razeni, 0 - name, 1 - surname, ...)
   * @return List<User>
   */
  public List<User> findUsers(
    String name,
    String surname,
    String address,
    String personalID,
    int sortedBy
  ) {
    return null;
  }

  /**
   * Vytvori uzivatele. Uzivatel bude po vlozeni nepotvrzen se stavem WAITING (cekajici na potvrzeni).
   * @param user Novy uzivatel
   * @return True: operace se uspesne podarila
   */
  public void createUser(User user) throws Exception {
    if (user == null) {
      throw new Exception("User is not defined");
    }
    if (user.getName().length() == 0) {
      throw new Exception("Name is not defined");
    }
    if (user.getSurname().length() == 0) {
      throw new Exception("Surname is not defined");
    }
    if (user.getAddress().length() == 0) {
      throw new Exception("Address is not defined");
    }
    if (user.getPersonalID().length() == 0) {
      throw new Exception("Personal ID is not defined");
    }
    if (user.getUsername().length() == 0) {
      throw new Exception("Username is not defined");
    }
    if (user.getPassword().length() < GlobalConfig.MIN_PASSWORD_LENGTH) {
      throw new Exception(
        "Minimum password length is " + GlobalConfig.MIN_PASSWORD_LENGTH
      );
    }
    user.setId(this.userRepository.count());
    user.encodePassword();
    this.userRepository.save(user);
  }

  /**
   * Editace parametru uzivatele. Neni mozne menit heselo a username
   * @param id ID uzivatele
   * @param user Nove parametry uzivatele
   * @return True: operace se uspesne podarila
   */
  public void editUser(long id, User user) throws Exception {}

  /**
   * Odstrani uzivatele s databaze
   * @param id ID uzivatele
   * @return True: operace se uspesne podarila
   */
  public void removeUser(long id) throws Exception {}

  /**
   * Zmeni heslo uzivatele
   * @param oldPass Stare heslo (aktualni)
   * @param newPass Nove heslo
   * @return True: operace se uspesne podarila
   */
  public void changePassword(String oldPass, String newPass) throws Exception {}

  /**
   * Nastaveni stavu profilu (WAITING, NOT_CONFIRMED, CONFIRMED, BANNED)
   * @param id ID uzivatele
   * @param state Stav
   * @return True: operace se uspesne podarila
   */
  public void setProfileState(long id, EProfileState state) throws Exception {}
}
