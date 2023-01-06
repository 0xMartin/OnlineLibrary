package cz.utb.fai.LibraryApp.model;

import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici stav uzivatelskeho profilu
 */
@Document("ProfileStates")
@Data
public class ProfileState {

  @Id
  @Indexed(unique=true)
  private Long id;

  /**
   * Nazev stavu, ve kterem se aktualne profil uzivatele nachazi
   */
  @Field("name")
  private EProfileState name;

  /**
   * Defaultni konstruktor
   */
  public ProfileState() {}

  public ProfileState(Long id, EProfileState name) {
    this.id = id;
    this.name = name;
  }

}
