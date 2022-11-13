package cz.utb.fai.LibraryApp.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.utb.fai.LibraryApp.SecurityConfig;

/**
 * Trida reprezentujici uzivatele
 */
@Document("Users")
@Data
public class User {

  @Id
  private long id;

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
  @Field("personaID")
  private String personaID;

  /**
   * Adresa uzivatele
   */
  @Field("address")
  private String address;

  /**
   * Uzivatelske jmeno (pro prihlasovani)
   */
  @Field("username")
  private String username;

  /**
   * Heslo uzivatele (hash)
   */
  @Field("password")
  private String password;

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
  @DocumentReference(lazy = true)
  private List<Borrow> borrows;

  /**
   * Vytvori instanci uzivatele
   *
   * @param id        ID uzivatele
   * @param name      Jmeno uzivatele
   * @param surname   Prijmeni uzivatele
   * @param personaID Rodne cislo uzivatele
   * @param address   Adresa uzivatele
   * @param username  Uzivatelske jmeno
   * @param password  Heslo uzivatele
   * @param state     Stav profilu (vice v ./enums/EProfileState.java)
   * @param role      Role uzivatle (vice v ./enums/ERole.java)
   */
  public User(
    long id,
    String name,
    String surname,
    String personaID,
    String address,
    String username,
    String password,
    ProfileState state,
    Role role
  ) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.personaID = personaID;
    this.address = address;
    this.username = username;
    this.password = SecurityConfig.encoder().encode(password);
    this.state = state;
    this.role = role;
  }
  
  public void setPassword(String password) {
    this.password = SecurityConfig.encoder().encode(password);
  }

}
