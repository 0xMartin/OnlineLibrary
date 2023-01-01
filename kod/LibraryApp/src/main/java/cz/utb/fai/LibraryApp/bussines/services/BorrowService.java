package cz.utb.fai.LibraryApp.bussines.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.model.Book;
import cz.utb.fai.LibraryApp.model.Borrow;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.BorrowRepository;

@Service
public class BorrowService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected BorrowRepository borrowRepository;

    /**
     * Najde vypujcku knihy v databazi podle jejiho ID
     * 
     * @param id ID vypujcky
     * @return Vypujcku knihy
     * @throws Exception
     */
    public Borrow findBorrow(long id) throws Exception {
        Optional<Borrow> book = this.borrowRepository.findById(id);
        if (!book.isPresent()) {
            throw new Exception(String.format("Borrow with ID [%d] not exists", id));
        }
        return book.get();
    }

    /**
     * Navrati seznam vsech vypujcek knih z databaze
     * 
     * @return Seznam vsech vypujcek knih
     */
    public List<Borrow> borrows() {
        return this.borrowRepository.findAll();
    }

    /**
     * Akutualne prihlaseny uzivatel si vypujci knihu
     * 
     * @param book - Kniha kterou si vyupujci
     * @return True -> vypujceni probehlo uspesne
     * @throws Exception
     */
    public boolean borrowBook(Book book) throws Exception {
        User profile = this.userService.profile();

        java.util.Date utilDate = new java.util.Date();

        Borrow b = new Borrow(
                (Long) this.borrowRepository.count(),
                GlobalConfig.BORROW_DAY_COUNT * 24 * 3600,
                new Date(utilDate.getTime()),
                false,
                profile,
                book);

        this.borrowRepository.save(b);

        return true;
    }

}
