package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.model.Book;

/**
 * Rozhrani pristukujici ke kolekci knih v databazi
 */
public interface BookRepository extends MongoRepository<Book, Long> {
    
    @Query("{name:'?0'}")
    Book findItemByName(String name);

    @Query("{author:'?0'}")
    Book findItemByAuthor(String author);

    @Query("{yearOfPublication:'?0'}")
    Book findItemByYear(String year);

    public long count();
}
