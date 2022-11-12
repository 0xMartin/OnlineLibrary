package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.model.Borrow;

public interface BorrowRepository extends MongoRepository<Borrow, Long> {

    @Query("{name:'?0'}")
    Borrow findItemByName(String name);

    public long count();
}
