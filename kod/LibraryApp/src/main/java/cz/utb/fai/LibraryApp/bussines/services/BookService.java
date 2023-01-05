package cz.utb.fai.LibraryApp.bussines.services;

import cz.utb.fai.LibraryApp.model.Book;
import cz.utb.fai.LibraryApp.repository.BookRepository;
import cz.utb.fai.LibraryApp.repository.BorrowHistoryRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servis pro spravu knih
 */
@Service
public class BookService {

  @Autowired
  protected BookRepository bookRepository;

  @Autowired
  protected BorrowHistoryRepository borrowHistoryRepository;

  @Autowired
  protected ImageService imageService;

  /**
   * Najde knizku v databazi podle jejiho ID
   * 
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
   * 
   * @return Seznam vsech knih
   */
  public List<Book> books() {
    return this.bookRepository.findAll();
  }

  @Autowired
  MongoTemplate mongoTemplate;

  /**
   * V databazi vyhleda knihy, kteri splnuji specifikovane pozadavky vyhledavani
   * 
   * @param name              Jmeno knihy
   * @param author            Autor knihy
   * @param yearOfPublication Rok vydani knihy
   * @param sortedBy          Razeni podle parametru (negativni cislo = bez
   *                          razeni, 0 - name, 1 - author, ...)
   * @param sortingASC        Zpusob razeni (true -> vzestupne, false -> sestupne)
   * @return Filtrovany seznam knih
   */
  public List<Book> findBooks(
      String name,
      String author,
      String yearOfPublication,
      int sortedBy,
      boolean sortingASC) {

    List<Criteria> criterias = new LinkedList<>();

    // vyhledavani podle jmena knihy
    if (name.length() > 2) {
      criterias.add(Criteria.where("name").regex(name, "i"));
    }

    // vyhledavani podle autora
    if (author.length() > 2) {
      criterias.add(Criteria.where("author").regex(author, "i"));
    }

    // vyhledavani podle roku
    if (yearOfPublication.length() > 3) {
      try {
        criterias.add(Criteria.where("yearOfPublication").is(Long.parseLong(yearOfPublication)));
      } catch (Exception ex) {
      }
    }

    Query query = null;
    if (criterias.isEmpty()) {
      query = Query.query(new Criteria());
    } else {
      query = Query.query(new Criteria().andOperator(criterias));
    }

    if (query == null) {
      return null;
    }

    // razeni
    switch (sortedBy) {
      case 0:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        break;
      case 1:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "author"));
        break;
      case 2:
        query.with(Sort.by(sortingASC ? Sort.Direction.ASC : Sort.Direction.DESC, "yearOfPublication"));
        break;
    }

    // provede dotaz na fitrovane vyhledavani
    return mongoTemplate.find(query, Book.class);
  }

  /**
   * Vytvori novou knihu
   * 
   * @param book  Nova kniha
   * @param image Obrazek knihy
   * @throws Exception
   */
  public void createBook(Book book, MultipartFile image) throws Exception {
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

    String imageUrl = this.imageService.uploadImage(image);

    book.setId(this.bookRepository.count());
    book.setImage(imageUrl);

    this.bookRepository.save(book);
  }

  /**
   * Upravy parametry knihy
   * 
   * @param id   ID knihy v databazi, ktera bude upravovana
   * @param book Nove parametry knihy
   * @return Nova kniha
   * @throws Exception
   */
  public Book updateBook(long id, Book book) throws Exception {
    if (book == null) {
      throw new Exception("Book is not defined");
    }

    if (book.getName().length() == 0) {
      throw new Exception("Name is not defined");
    }
    if (book.getAuthor().length() == 0) {
      throw new Exception("Author is not defined");
    }
    if (book.getPageCount() <= 0) {
      throw new Exception("Page count cant be negative or zero");
    }
    if (book.getYearOfPublication() <= 0) {
      throw new Exception("Year cant be negative or zero");
    }
    if (book.getAvailable() <= 0) {
      throw new Exception("Available count cant be negative or zero");
    }

    Book book_Db = this.findBook(id);
    book_Db.setName(book.getName());
    book_Db.setAuthor(book.getAuthor());
    book_Db.setPageCount(book.getPageCount());
    book_Db.setYearOfPublication(book.getYearOfPublication());
    book_Db.setAvailable(book.getAvailable());

    // ulozeni zmen v databazi
    this.bookRepository.save(book_Db);

    return book_Db;
  }

  /**
   * Odstrani z databaze knihu
   * 
   * @param id ID knihy, ktera bude odstranena
   * @throws Exception
   */
  public void removeBook(Long id) throws Exception {
    Book book = this.findBook(id);

    // pokud ma nekdo vypujcenou knihu neni ji mozne odstranit
    if (!book.getBorrows().isEmpty()) {
      throw new Exception("Some users still have this book in the borrow list");
    }

    // odstraneni obrazku
    this.imageService.readImage(book.getImage());

    // odstraneni knihy
    this.bookRepository.delete(book);
  }
}
