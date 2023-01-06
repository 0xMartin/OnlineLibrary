package cz.utb.fai.LibraryApp.model;

import cz.utb.fai.LibraryApp.SecurityConfig;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Trida reprezentujici uzivatele
 */
@Document("Users")
@Data
public class User {

  /**
   * ID = Uzivatelske jmeno (pro prihlasovani)
   */
  @Id
  @Indexed(unique = true)
  private String username;

  /**
   * Heslo uzivatele (hash)
   */
  @Field("password")
  private String password;

  /**
   * Jmeno uzivatele
   */
  @Field("name")
  private String name;

  /**
   * Prijmeni uzivatele
   */
  @Field("surname")
  private String surname;

  /**
   * Rodne cislo uzivatele
   */
  @Field("personalID")
  private String personalID;

  /**
   * Adresa uzivatele
   */
  @Field("address")
  private String address;

  /**
   * Stav profilu uzivatele, ve kterem se aktualne nachazi
   */
  @DocumentReference(lazy = false)
  @Field("state")
  private ProfileState state;

  /**
   * Role uzivatele
   */
  @DocumentReference(lazy = false)
  @Field("role")
  private Role role;

  /**
   * Seznam vsech vypujcek knih
   */
  @JsonIgnore
  @DocumentReference(lazy = false, lookup = "{'user_id': ?#{#self._id} }")
  private List<Borrow> borrows;

  /**
   * Historie vsech vypujcenych knih
   */
  @JsonIgnore
  @DocumentReference(lazy = false, lookup = "{'user_id': ?#{#self._id} }")
  private List<BorrowHistory> borrowhistory;

  /**
   * Defaultni konstruktor
   */
  public User() {
  }

  /**
   * Vytvori instanci uzivatele
   *
   * @param username  Uzivatelske jmeno
   * @param password  Heslo uzivatele
   * @param name      Jmeno uzivatele
   * @param surname   Prijmeni uzivatele
   * @param personaID Rodne cislo uzivatele
   * @param address   Adresa uzivatele
   * @param state     Stav profilu (vice v ./enums/EProfileState.java)
   * @param role      Role uzivatle (vice v ./enums/ERole.java)
   */
  public User(
      String username,
      String password,
      String name,
      String surname,
      String personalID,
      String address,
      ProfileState state,
      Role role) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.personalID = personalID;
    this.address = address;
    this.state = state;
    this.role = role;
  }

  /**
   * Zasifruje heslo. Pouzit jen v pripade pokud jde o noveho uzivatele, ktery
   * bude nasledne pridan do databaze.
   * 
   * @return User
   */
  public User encodePassword() {
    this.password = SecurityConfig.encoder().encode(this.password);
    return this;
  }
}
