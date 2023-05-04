package cz.utb.fai.LibraryApp.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici vypujceni knihy
 */
@Document("Borrows")
@Data
public class Borrow {

  /**
   * Unikatni ID entity databaze
   */
  @Transient
  public static final String ID = "BORROW";

  @Id
  @Indexed(unique = true)
  private Long id;

  /**
   * Datum vypujceni knihy
   */
  @Field("date")
  private Date date;

  /**
   * Datum kdy expiruje vypujka knihy
   */
  @Indexed(name = "expireAt", expireAfterSeconds = 0)
  private Date expireAt;

  /**
   * ID Uzivatele, ktery si knihu pujcil
   */
  @Field("user_id")
  private String user_id;

  /**
   * Knihy, kterou si uzivatel vypujcil
   */
  @Field("book_id")
  @DocumentReference(lazy = false)
  private Book book;

  /**
   * Defaultni konstruktor
   */
  public Borrow() {
  }

  /**
   * Vytvori instanci reprezentujici vypujceni knihy
   *
   * @param id      ID vypujceni
   *                vytvoreni vypujcky)
   * @param date    Datum vytvoreni vypujcky knihy
   * @param seconds Na kolik sekund bude kniha vypujcena
   * @param user_id Uzivatel, ktery si knihu vypujcil
   * @param book    Knihy, kterou si uzivatel vypujcil
   */
  public Borrow(
      Long id,
      Date date,
      Long seconds,
      String user_id,
      Book book) {
    this.id = id;
    this.date = date;
    this.user_id = user_id;
    this.book = book;

    Calendar cal = Calendar.getInstance();
    cal.setTime(this.date);
    cal.add(Calendar.SECOND, (int) seconds.longValue());
    this.expireAt = cal.getTime();
  }

}
