package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.model.Book;
import cz.utb.fai.LibraryApp.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servis pro spravu knih
 */
@Service
public class BookService {

  @Autowired
  protected BookRepository bookRepository;

  @Autowired
  protected ImageService imageService;

  /**
   * Najde knizku v databazi podle jejiho ID
   * @param id ID hledane knihy
   * @return Knihy
   * @throws Exception
   */
  public Book findBook(long id) throws Exception {
    Optional<Book> book = this.bookRepository.findById(id);
    if (!book.isPresent()) {
      throw new Exception(String.format("Book with ID [%d] not exists", id));
    }
    return book.get();
  }

  /**
   * Navrati seznam vsech knih z databaze
   * @return Seznam vsech knih
   */
  public List<Book> books() {
    return this.bookRepository.findAll();
  }

  /**
   * V databazi vyhleda uzivatele, kteri splnuji specifikovane pozadavky vyhledavani
   * @param name Jmeno knihy
   * @param author Autor knihy
   * @param yearOfPublication Rok vydani knihy
   * @param sortedBy Razeni podle parametru (negativni cislo = bez razeni, 0 - name, 1 - author, ...)
   * @return Filtrovany seznam knih
   */
  public List<Book> findUsers(
    String name,
    String author,
    long yearOfPublication,
    int sortedBy
  ) {
    return null;
  }

  /**
   * Vytvori novou knihu
   * @param book Nova kniha
   * @throws Exception
   */
  public void createBook(Book book) throws Exception {
    if (book == null) {
      throw new Exception("Book is not defined");
    }

    if (book.getName().length() == 0) {
      throw new Exception("Name is not defined");
    }
    if (book.getAuthor().length() == 0) {
      throw new Exception("Name is not defined");
    }
    if (book.getYearOfPublication() < 0) {
      throw new Exception("Year must be positive value");
    }
    if (book.getAvailable() < 0) {
      throw new Exception("Available must be positive value");
    }

    book.setId(this.bookRepository.count());
    book.setBorrowed(0);
    
    this.bookRepository.save(book);
  }

  /**
   * Upravy parametry knihy
   * @param id ID knihy v databazi, ktera bude upravovana
   * @param book Nove parametry knihy
   * @return Nova kniha
   * @throws Exception
   */
  public Book updateBook(long id, Book book) throws Exception {
    return null;
  }

  /**
   * Odstrani z databaze knihu
   * @param id ID knihy, ktera bude odstranena
   * @throws Exception
   */
  public void deleteBook(long id) throws Exception {}
}
