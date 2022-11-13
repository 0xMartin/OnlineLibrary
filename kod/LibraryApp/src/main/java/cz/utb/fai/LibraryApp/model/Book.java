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
  private long id;

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
  private long pageCount;

  /**
   * Rok vydani knihy
   */
  @Field("yearOfPublication")
  private long yearOfPublication;

  /**
   * Obrazek knihy
   */
  @Field("image")
  private String image;

  /**
   * Pocet dostupnych knih v knihovne
   */
  @Field("available")
  private long available;

  /**
   * Aktualni pocet vypujcenych kusu teto knihy
   */
  @Field("borrowed")
  private long borrowed;

  /**
   * Seznam vsech vypujcek teto knihy
   */
  @DocumentReference(lazy = true)
  private List<Borrow> borrows;

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
    long id,
    String name,
    String author,
    long pageCount,
    long yearOfPublication,
    String image,
    long available,
    long borrowed
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
}
