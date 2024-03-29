package cz.utb.fai.LibraryApp.model;

import cz.utb.fai.LibraryApp.bussines.enums.ERole;
import lombok.Data;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici roli uzivatele
 */
@Document("Roles")
@Data
public class Role {

  /**
   * Unikatni ID entity databaze
   */
  @Transient
  public static final String ID = "ROLE";

  @Id
  @Indexed(unique = true)
  private Long id;

  /**
   * Nazev uzivatelske role
   */
  @Field("name")
  private ERole name;

  /**
   * Defaultni konstruktor
   */
  public Role() {
  }

  public Role(Long id, ERole name) {
    this.id = id;
    this.name = name;
  }

}
