package cz.utb.fai.LibraryApp.model;

import java.sql.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Books")
@Data
public class Borrow {

  @Id
  private Long id;

  /**
   * Za kolik dni bude vypujcka knihy expirovat (od data
   * vytvoreni vypujcky)
   */
  @Field("expiration")
  private Long expiration;

  /**
   * Datum vypujceni knihy
   */
  @Field("date")
  private Date date;

  /**
   * Stav o tom zda kniha byla jiz vracena
   */
  @Field("returned")
  private Boolean returned;

  /**
   * Uzivatel, ktery si knihu pujcil
   */
  @DocumentReference(lazy = false)
  @Field("user")
  private User user;

  /**
   * Knihy, kterou si uzivatel vypujcil
   */
  @DocumentReference(lazy = false)
  @Field("book")
  private Book book;

  /**
   * Defaultni konstruktor
   */
  public Borrow() {}

  /**
   * Vytvori instanci reprezentujici vypujceni knihy
   *
   * @param id         ID vypujceni
   * @param expiration Za kolik dni bude vypujcka knihy expirovat (od data
   *                   vytvoreni vypujcky)
   * @param date       Datum vytvoreni vypujcky knihy
   * @param returned   Stav o tom zda kniha byla jiz vrace
   * @param user       Uzivatel, ktery si knihu vypujcil
   * @param book       Knihy, kterou si uzivatel vypujcil
   */
  public Borrow(
    Long id,
    long expiration,
    Date date,
    boolean returned,
    User user,
    Book book
  ) {
    this.id = id;
    this.expiration = expiration;
    this.date = date;
    this.returned = returned;
    this.user = user;
    this.book = book;
  }
}
