package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.SecurityConfig;
import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import cz.utb.fai.LibraryApp.model.ProfileState;
import cz.utb.fai.LibraryApp.model.Role;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.*;
import java.util.List;
import java.util.Optional;
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

  @Autowired
  protected RoleRepository roleRepository;

  @Autowired
  protected ProfileStateRepository profileStateRepository;

  /**
   * Navrati vsechny informace o aktualne prihlasenem uzivateli (profil uzivatele)
   * @return User
   */
  public User profile() throws Exception {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentPrincipalName = authentication.getName();
      Optional<User> user = this.userRepository.findById(currentPrincipalName);
      if (user.isPresent()) {
        return user.get();
      } else {
        throw new Exception("User is not logged in");
      }
    } else {
      throw new Exception("User is not logged in");
    }
  }

  /**
   * V datatbazi najde uzivatele s konkretnim ID
   * @param username Uzivatelske jmeno
   * @return User
   */
  public User findUser(String username) throws Exception {
    Optional<User> user = this.userRepository.findById(username);
    if (!user.isPresent()) {
      throw new Exception(String.format("%s not found", username));
    }
    return user.get();
  }

  /**
   * Navrati vsechny uzivatle
   * @return List<User>
   */
  public List<User> users() {
    return this.userRepository.findAll();
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
   * Navrati vsechny uzivatele jejihz profil ma stav WAITING
   * @return List<User>
   */
  public List<User> findUsersWithWaitingState() throws Exception {
    ProfileState state =
      this.profileStateRepository.findItemByName(EProfileState.WAITING);
    if (state == null) {
      throw new Exception("WAITING state not exists");
    }
    long id = state.getId();
    return this.userRepository.findAll()
      .stream()
      .filter(u -> u.getState().getId() == id)
      .toList();
  }

  /**
   * Vytvori uzivatele. Uzivatel bude po vlozeni nepotvrzen se stavem WAITING (cekajici na potvrzeni).
   * @param user Novy uzivatel
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

    if (this.userRepository.findById(user.getUsername()).isPresent()) {
      throw new Exception(
        String.format(
          "User with %s username already exists",
          user.getUsername()
        )
      );
    }

    Role role = roleRepository.findItemByName(ERole.CUSTOMER);
    if (role == null) {
      throw new Exception("Role setup error");
    }
    user.setRole(role);

    ProfileState state = profileStateRepository.findItemByName(
      EProfileState.WAITING
    );
    if (state == null) {
      throw new Exception("Profile state setup error");
    }
    user.setState(state);

    user.encodePassword();
    this.userRepository.save(user);
  }

  /**
   * Editace parametru uzivatele. Neni mozne menit heselo a username
   * @param username Uzivatelske jmeno
   * @param user Nove parametry uzivatele
   * @return Nove uzivatelske data
   */
  public User editUser(String username, User user) throws Exception {
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

    User user_Db = this.findUser(username);
    user_Db.setName(user.getName());
    user_Db.setSurname(user.getSurname());
    user_Db.setAddress(user.getAddress());
    user_Db.setPersonalID(user.getPersonalID());

    //state
    if (user_Db.getRole().getName() != ERole.LIBRARIAN) {
      ProfileState state =
        this.profileStateRepository.findItemByName(EProfileState.WAITING);
      if (state == null) {
        throw new Exception("WAITING state not found");
      }
      user_Db.setState(state);
    }

    this.userRepository.save(user_Db);

    return user_Db;
  }

  /**
   * Odstrani uzivatele s databaze
   * @param username Uzivatelske jmeno
   */
  public void removeUser(String username) throws Exception {
    User user = this.findUser(username);

    //String.format("Failed to remove '%s'", username)
    // vraceni knih zpatky (dekrementuje pocet vypujcenych knih)
    user
      .getBorrows()
      .forEach(b -> {
        b.getBook().decrementBorrowed();
      });

    this.userRepository.delete(user);
  }

  /**
   * Zmeni heslo uzivatele
   * @param currentPass Aktualni heslo
   * @param newPass Nove heslo
   */
  public void changePassword(String currentPass, String newPass)
    throws Exception {
    User profile = this.profile();
    if (!SecurityConfig.encoder().matches(currentPass, profile.getPassword())) {
      throw new Exception("Current password does not match");
    }
    if (newPass.length() < GlobalConfig.MIN_PASSWORD_LENGTH) {
      throw new Exception(
        "Minimum password length is " + GlobalConfig.MIN_PASSWORD_LENGTH
      );
    }
    profile.setPassword(newPass);
    profile.encodePassword();
    this.userRepository.save(profile);
  }

  /**
   * Nastaveni stavu profilu (WAITING, NOT_CONFIRMED, CONFIRMED, BANNED)
   * @param username Uzivatelske jmeno
   * @param state Stav
   */
  public void setProfileState(String username, EProfileState state)
    throws Exception {
    User user = this.findUser(username);

    if (this.profile().getUsername().equals(username)) {
      throw new Exception("You cannot modify the state of your own profile");
    }

    ProfileState s = this.profileStateRepository.findItemByName(state);
    if (s == null) {
      throw new Exception(String.format("%s not found", state));
    }
    user.setState(s);
    this.userRepository.save(user);
  }
}
