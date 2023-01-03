package cz.utb.fai.LibraryApp.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Trida reprezentujici knihu
 */
@Document("Books")
@Data
public class Book {

  @Id
  private Long id;

  /**
   * Jmeno knihy
   */
  @Field("name")
  private String name;

  /**
   * Autor knihy
   */
  @Field("author")
  private String author;

  /**
   * Pocet stran knihy
   */
  @Field("pageCount")
  private Long pageCount;

  /**
   * Rok vydani knihy
   */
  @Field("yearOfPublication")
  private Long yearOfPublication;

  /**
   * Obrazek knihy
   */
  @Field("image")
  private String image;

  /**
   * Pocet dostupnych knih v knihovne
   */
  @Field("available")
  private Long available;

  /**
   * Aktualni pocet vypujcenych kusu teto knihy
   */
  @Field("borrowed")
  private Long borrowed;

  /**
   * Seznam vsech vypujcek teto knihy
   */
  @DocumentReference(lazy = false, lookup="{'book_id': ?#{#self._id} }")
  private List<Borrow> borrows;

  /**
   * Defaultni konstruktor
   */
  public Book() {}

  /**
   * Vytvori instanci knihy
   *
   * @param id                ID knihy
   * @param name              Jmeno knihy
   * @param author            Autor knihy
   * @param pageCount         Pocet stran knihy
   * @param yearOfPublication Rok vydani knihy
   * @param image             Obrazek knihy
   * @param available         Pocet dostupnych knih v knihovne
   * @param borrowed          Aktualni pocet vypujcenych kusu teto knihy
   */
  public Book(
    Long id,
    String name,
    String author,
    Long pageCount,
    Long yearOfPublication,
    String image,
    Long available,
    Long borrowed
  ) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.pageCount = pageCount;
    this.yearOfPublication = yearOfPublication;
    this.image = image;
    this.available = available;
    this.borrowed = borrowed;
  }

  /**
   * Inkrementuje pocet vypujcenych knih
   */
  public void incrementBorrowed() {
    this.borrowed = Math.min(this.available, this.borrowed + 1);
  }

  /**
   * Dekrementuje pocet vypujcenych knih
   */
  public void decrementBorrowed() {
    this.borrowed = Math.max(0, this.borrowed - 1);
  }
  
}
