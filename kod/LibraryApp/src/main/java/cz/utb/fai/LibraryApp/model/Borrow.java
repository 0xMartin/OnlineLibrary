package cz.utb.fai.LibraryApp.model;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Borrows")
@Data
public class Borrow {

  @Id
  private Long id;

  /**
   * Datum vypujceni knihy
   */
  @Field("date")
  @Indexed(expireAfterSeconds = 12000) //(int) GlobalConfig.BORROW_DAY_COUNT
  private Date date;

  /**
   * Uzivatel, ktery si knihu pujcil
   */
  @Field("user_id")
  @DocumentReference(lazy = false)
  private User user;

  /**
   * Knihy, kterou si uzivatel vypujcil
   */
  @Field("book_id")
  @DocumentReference(lazy=false)
  private Book book;

  /**
   * Defaultni konstruktor
   */
  public Borrow() {}

  /**
   * Vytvori instanci reprezentujici vypujceni knihy
   *
   * @param id         ID vypujceni
   *                   vytvoreni vypujcky)
   * @param date       Datum vytvoreni vypujcky knihy
   * @param user       Uzivatel, ktery si knihu vypujcil
   * @param book       Knihy, kterou si uzivatel vypujcil
   */
  public Borrow(
    Long id,
    Date date,
    User user,
    Book book
  ) {
    this.id = id;
    this.date = date;
    this.user = user;
    this.book = book;
  }
}
