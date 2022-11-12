package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.models.Book;

public interface BookRepository extends MongoRepository<Book, Long> {
    
    @Query("{name:'?0'}")
    Book findItemByName(String name);

}
