package cz.utb.fai.LibraryApp.model;

import cz.utb.fai.LibraryApp.enums.ERole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici roli uzivatele
 */
@Document("Roles")
@Data
public class Role {

  @Id
  private long id;

  /**
   * Nazev uzivatelske role
   */
  @Field("name")
  private ERole name;

  public Role(long id, ERole role) {
    this.id = id;
    this.name = role;
  }
}
