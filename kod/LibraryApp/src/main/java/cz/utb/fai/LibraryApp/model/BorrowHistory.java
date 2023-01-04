package cz.utb.fai.LibraryApp.model;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("BorrowsHistory")
@Data
public class BorrowHistory {

  @Id
  @Indexed(unique=true)
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
  @DocumentReference(lazy = false)
  private User user;

  /**
   * Info o vypujcene knize (nepouziva se Book aby bylo mozne historii zobrazovat i po odstraneni knihy)
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
   * @param id      ID vypujceni
   *                vytvoreni vypujcky)
   * @param date    Datum vytvoreni vypujcky knihy
   * @param seconds Na kolik sekund bude kniha vypujcena
   * @param user    Uzivatel, ktery si knihu vypujcil
   * @param book    Knihy, kterou si uzivatel vypujcil
   */
  public BorrowHistory(
      Long id,
      Date date,
      long seconds,
      User user,
      Book book) {
    this.id = id;
    this.date = date;
    this.user = user;
    this.book_id = book.getId();
    this.book_name = book.getName();
    this.book_author = book.getAuthor();
  }

  /**
   * Vytvori instanci reprezentujici historii vypujceni knihy
   * 
   * @param id     - ID vypujcky
   * @param borrow - Odkaz na vypujcku knihy ze ktere bude tato instance inicializovana
   */
  public BorrowHistory(Long id, Borrow borrow) {
    this.id = id;
    this.date = borrow.getDate();
    this.user = borrow.getUser();
    
    Book book = borrow.getBook();
    this.book_id = book.getId();
    this.book_name = book.getName();
    this.book_author = book.getAuthor();
  }

}
