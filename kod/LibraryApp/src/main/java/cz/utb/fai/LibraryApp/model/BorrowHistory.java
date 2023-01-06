package cz.utb.fai.LibraryApp.model;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici historii vypujceni knihy
 */
@Document("BorrowsHistory")
@Data
public class BorrowHistory {

  @Id
  @Indexed(unique = true)
  private Long id;

  /**
   * Datum vypujceni knihy
   */
  @Field("date")
  private Date date;

  /**
   * Uzivatel, ktery si knihu pujcil
   */
  @Field("user_id")
  private String user_id;

  /**
   * Info o vypujcene knize (nepouziva se Book aby bylo mozne historii zobrazovat
   * i po odstraneni knihy)
   */
  @Field("book_id")
  private Long book_id;

  @Field("book_name")
  private String book_name;

  @Field("book_author")
  private String book_author;

  /**
   * Defaultni konstruktor
   */
  public BorrowHistory() {
  }

  /**
   * Vytvori instanci reprezentujici historii vypujceni knihy
   *
   * @param id          ID vypujceni
   *                    
   * @param date        Datum vytvoreni vypujcky knihy
   * @param user_id     Uzivatel, ktery si knihu vypujcil
   * @param book_id     ID vypujcene knihy
   * @param book_name   Jmeno vypujcene knihy
   * @param book_author Autor vypujcene knihy
   */
  public BorrowHistory(
      Long id,
      Date date,
      String user_id,
      Long book_id,
      String book_name,
      String book_author) {
    this.id = id;
    this.date = date;
    this.user_id = user_id;
    this.book_id = book_id;
    this.book_name = book_name;
    this.book_author = book_author;
  }

  /**
   * Vytvori instanci reprezentujici historii vypujceni knihy
   * 
   * @param id     - ID vypujcky
   * @param borrow - Odkaz na vypujcku knihy ze ktere bude tato instance
   *               inicializovana
   */
  public BorrowHistory(Long id, Borrow borrow) {
    this.id = id;
    this.date = borrow.getDate();
    this.user_id = borrow.getUser_id();

    Book book = borrow.getBook();
    this.book_id = book.getId();
    this.book_name = book.getName();
    this.book_author = book.getAuthor();
  }

}
