package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servis pro spravu uzivatelu
 */
@Service
public class UserService {

  @Autowired
  protected UserRepository UserRepository;

  /**
   * Navrati vsechny informace o aktualne prihlasenem uzivateli (profil uzivatele)
   * @return User
   */
  public User profile() {
    return null;
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
  public boolean createUser(User user) {
    return true;
  }

  /**
   * Editace parametru uzivatele. Neni mozne menit heselo a username
   * @param id ID uzivatele
   * @param user Nove parametry uzivatele
   * @return True: operace se uspesne podarila
   */
  public boolean editUser(long id, User user) {
    return true;
  }

  /**
   * Odstrani uzivatele s databaze
   * @param id ID uzivatele
   * @return True: operace se uspesne podarila
   */
  public boolean removeUser(long id) {
    return true;
  }

  /**
   * Zmeni heslo uzivatele
   * @param oldPass Stare heslo (aktualni)
   * @param newPass Nove heslo
   * @return True: operace se uspesne podarila
   */
  public boolean changePassword(String oldPass, String newPass) {
    return true;
  }

  /**
   * Nastaveni stavu profilu (WAITING, NOT_CONFIRMED, CONFIRMED, BANNED)
   * @param id ID uzivatele
   * @param state Stav
   * @return True: operace se uspesne podarila
   */
  public boolean setProfileState(long id, EProfileState state) {
    return true;
  }
  
}
