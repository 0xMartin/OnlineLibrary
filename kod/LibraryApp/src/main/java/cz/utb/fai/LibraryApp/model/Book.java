package cz.utb.fai.LibraryApp.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Trida reprezentujici knihu
 */
@Document("Books")
@Data
public class Book {

  @Id
  @Indexed(unique = true)
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
   * Strucny popis knihy
   */
  @Field("description")
  private String description;

  /**
   * Obrazek knihy (URL)
   */
  @Field("image")
  @DocumentReference(lazy = false)
  private Image image;

  /**
   * Pocet dostupnych knih v knihovne
   */
  @Field("available")
  private Long available;

  /**
   * Seznam vsech vypujcek teto knihy
   */
  @JsonIgnore
  @DocumentReference(lazy = false, lookup = "{'book_id': ?#{#self._id}}")
  private List<Borrow> borrows;

  /**
   * Defaultni konstruktor
   */
  public Book() {
  }

  /**
   * Vytvori instanci knihy
   *
   * @param id                ID knihy
   * @param name              Jmeno knihy
   * @param author            Autor knihy
   * @param pageCount         Pocet stran knihy
   * @param yearOfPublication Rok vydani knihy
   * @param description       Popis knihy
   * @param image             Obrazek knihy
   * @param available         Pocet dostupnych knih v knihovne
   */
  public Book(
      Long id,
      String name,
      String author,
      Long pageCount,
      Long yearOfPublication,
      String description,
      Image image,
      Long available) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.pageCount = pageCount;
    this.yearOfPublication = yearOfPublication;
    this.description = description;
    this.image = image;
    this.available = available;
  }

}
