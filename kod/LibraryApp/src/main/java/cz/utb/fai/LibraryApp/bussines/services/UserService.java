package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.SecurityConfig;
import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import cz.utb.fai.LibraryApp.model.ProfileState;
import cz.utb.fai.LibraryApp.model.Role;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.*;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

  @Autowired
  protected BorrowRepository borrowRepository;

  @Autowired
  protected BorrowHistoryRepository borrowHistoryRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  /**
   * Navrati vsechny informace o aktualne prihlasenem uzivateli (profil uzivatele)
   * 
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
   * 
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
   * 
   * @return List<User>
   */
  public List<User> users() {
    return this.userRepository.findAll();
  }

  /**
   * V databazi vyhleda uzivatele, kteri splnuji specifikovane pozadavky
   * vyhledavani
   * 
   * @param name       Jmeno uzivatele
   * @param surname    Prijmeni uzivatele
   * @param address    Adresa uzivatele
   * @param personalID Rodne cislo uzivatle
   * @param sortedBy   Razeni podle parametru (negativni cislo = bez razeni, 0 -
   *                   name, 1 - surname, ...)
   * @param sortingASC True -> razeni vzestupne, False -> razeni sestupne
   * @return List<User>
   */
  public List<User> findUsers(
      String name,
      String surname,
      String address,
      String personalID,
      int sortedBy,
      boolean sortingASC) {

    List<Criteria> criterias = new LinkedList<>();

    // vyhledavani podle jmena
    if (name.length() > 2) {
      criterias.add(Criteria.where("name").regex(name, "i"));
    }

    // vyhledavani podle prijmeni
    if (surname.length() > 2) {
      criterias.add(Criteria.where("surname").regex(surname, "i"));
    }

    // vyhledavani podle adresy
    if (address.length() > 2) {
      try {
        criterias.add(Criteria.where("address").regex(address, "i"));
      } catch (Exception ex) {
      }
    }

    // vyhledavani podle rodneho cisla
    if (personalID.length() > 2) {
      try {
        criterias.add(Criteria.where("personalID").regex(personalID, "i"));
      } catch (Exception ex) {
      }
    }

    Query query = null;
    if (criterias.isEmpty()) {
      query = Query.query(new Criteria());
    } else {
      query = Query.query(new Criteria().andOperator(criterias));
    }

    if (query == null) {
      return null;
    }

    // razeni
    switch (sortedBy) {
      case 0:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        break;
      case 1:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "surname"));
        break;
      case 2:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        break;
      case 3:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "personalID"));
        break;
    }

    // provede dotaz na fitrovane vyhledavani
    return mongoTemplate.find(query, User.class);
  }

  /**
   * Navrati vsechny uzivatele jejihz profil ma stav WAITING
   * 
   * @return List<User>
   */
  public List<User> findUsersWithWaitingState() throws Exception {
    Optional<ProfileState> state = this.profileStateRepository.findItemByName(EProfileState.WAITING);
    if (!state.isPresent()) {
      throw new Exception("WAITING state not exists");
    }
    long id = state.get().getId();
    return this.userRepository.findAll()
        .stream()
        .filter(u -> u.getState().getId() == id)
        .toList();
  }

  /**
   * Vytvori uzivatele. Uzivatel bude po vlozeni nepotvrzen se stavem WAITING
   * (cekajici na potvrzeni).
   * 
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
          "Minimum password length is " + GlobalConfig.MIN_PASSWORD_LENGTH);
    }

    if (this.userRepository.findById(user.getUsername()).isPresent()) {
      throw new Exception(
          String.format(
              "User with %s username already exists",
              user.getUsername()));
    }

    Optional<Role> role = roleRepository.findItemByName(ERole.CUSTOMER);
    if (!role.isPresent()) {
      throw new Exception("Role setup error");
    }
    user.setRole(role.get());

    Optional<ProfileState> state = profileStateRepository.findItemByName(
        EProfileState.WAITING);
    if (!state.isPresent()) {
      throw new Exception("Profile state setup error");
    }
    user.setState(state.get());

    user.encodePassword();
    user.setBorrowhistory(null);
    user.setBorrows(null);
    this.userRepository.insert(user);
  }

  /**
   * Editace parametru uzivatele. Neni mozne menit heselo a username
   * 
   * @param username Uzivatelske jmeno
   * @param user     Nove parametry uzivatele
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

    // state
    if (user_Db.getRole().getName() != ERole.LIBRARIAN) {
      Optional<ProfileState> state = this.profileStateRepository.findItemByName(EProfileState.WAITING);
      if (!state.isPresent()) {
        throw new Exception("WAITING state not found");
      }
      user_Db.setState(state.get());
    }

    user_Db.setBorrowhistory(null);
    user_Db.setBorrows(null);
    this.userRepository.save(user_Db);

    return user_Db;
  }

  /**
   * Odstrani uzivatele s databaze
   * 
   * @param username Uzivatelske jmeno
   */
  public void removeUser(String username) throws Exception {
    if (this.profile().getUsername().equals(username)) {
      throw new Exception("You can't delete yourself");
    }

    User user = this.findUser(username);

    // odstrani vypujcky knihy
    if (!user.getBorrows().isEmpty()) {
      this.borrowRepository.deleteAll(user.getBorrows());
    }

    // odstraneni historie uzivatele
    if (!user.getBorrowhistory().isEmpty()) {
      this.borrowHistoryRepository.deleteAll(user.getBorrowhistory());
    }

    // odstrani uzivatele
    this.userRepository.delete(user);
  }

  /**
   * Zmeni heslo uzivatele
   * 
   * @param currentPass Aktualni heslo
   * @param newPass     Nove heslo
   */
  public void changePassword(String currentPass, String newPass)
      throws Exception {
    User profile = this.profile();
    if (!SecurityConfig.encoder().matches(currentPass, profile.getPassword())) {
      throw new Exception("Current password does not match");
    }
    if (newPass.length() < GlobalConfig.MIN_PASSWORD_LENGTH) {
      throw new Exception(
          "Minimum password length is " + GlobalConfig.MIN_PASSWORD_LENGTH);
    }
    profile.setPassword(newPass);
    profile.encodePassword();
    this.userRepository.save(profile);
  }

  /**
   * Nastaveni stavu profilu (WAITING, NOT_CONFIRMED, CONFIRMED, BANNED)
   * 
   * @param username Uzivatelske jmeno
   * @param state    Stav
   */
  public void setProfileState(String username, EProfileState state)
      throws Exception {
    User user = this.findUser(username);

    if (this.profile().getUsername().equals(username)) {
      throw new Exception("You cannot modify the state of your own profile");
    }

    Optional<ProfileState> s = this.profileStateRepository.findItemByName(state);
    if (!s.isPresent()) {
      throw new Exception(String.format("%s not found", state));
    }
    user.setState(s.get());
    this.userRepository.save(user);
  }

  /**
   * Vygeneruje vybranemu uzivateli nove heslo
   * 
   * @param username Uzivatelske jmenu uzivatele kteremu bude vygenerovane nove
   *                 heslo
   * @return Nove heslo (paint text)
   * @throws Exception
   */
  public String generatePass(String username) throws Exception {
    if (this.profile().getUsername().equals(username)) {
      throw new Exception("You can't generate a password for yourself");
    }

    User user = this.findUser(username);

    String pass = UserService.generateRandomPassword(30, 48, 122);

    user.setPassword(pass);
    user.encodePassword();
    user.setBorrowhistory(null);
    user.setBorrows(null);
    this.userRepository.save(user);

    return pass;
  }

  public static String generateRandomPassword(int len, int randNumOrigin, int randNumBound) {
    SecureRandom random = new SecureRandom();
    return random.ints(randNumOrigin, randNumBound + 1)
        .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i))
        .limit(len)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();
  }

}
