package cz.utb.fai.LibraryApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cz.utb.fai.LibraryApp.model.Borrow;
import cz.utb.fai.LibraryApp.model.BorrowHistory;

/**
 * Rozhrani pristukujici ke kolekci vypujcek knih v databazi
 */
public interface BorrowHistoryRepository extends MongoRepository<BorrowHistory, Long> {

    @Query("{user_id:'?0'}")
    Borrow findItemByUser(String username);

    @Query("{book_id:'?0'}")
    Borrow findItemByBook(Long bookID);

    public long count();
    
}
