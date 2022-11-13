package cz.utb.fai.LibraryApp.model;

import cz.utb.fai.LibraryApp.enums.EProfileState;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici stav uzivatelskeho profilu
 */
@Document("ProfileStates")
@Data
public class ProfileState {

  @Id
  private long id;

  /**
   * Nazev stavu, ve kterem se aktualne profil uzivatele nachazi
   */
  @Field("name")
  private EProfileState name;

  public ProfileState(long id, EProfileState state) {
    this.id = id;
    this.name = state;
  }
}
