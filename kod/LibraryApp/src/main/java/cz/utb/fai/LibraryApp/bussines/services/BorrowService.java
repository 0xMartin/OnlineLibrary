package cz.utb.fai.LibraryApp.bussines.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.model.Book;
import cz.utb.fai.LibraryApp.model.Borrow;
import cz.utb.fai.LibraryApp.model.User;
import cz.utb.fai.LibraryApp.repository.BookRepository;
import cz.utb.fai.LibraryApp.repository.BorrowRepository;

@Service
public class BorrowService {

    @Autowired
    protected BorrowRepository borrowRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BookRepository bookRepository;

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
    public void borrowBook(Book book) throws Exception {
        User profile = this.userService.profile();

        List<Borrow> borrows = profile.getBorrows();
        if (borrows != null) {
            // overeni zda vypojcena knihy jiz neni timto uzivatelem vypujcena
            for (Borrow b : borrows) {
                if (b.getBook().getId().longValue() == book.getId().longValue()) {
                    throw new Exception("It is not possible to borrow the same book twice");
                }
            }

            // overeni poctu vypujcenych knih
            if (borrows.size() >= GlobalConfig.MAX_BORROWED_BOOKS) {
                throw new Exception("The limit of borrowed books has been exceeded");
            }
        }

        // inkrementuje pocet vypujcenych knih
        book.setBorrowed(book.getBorrowed() + 1);
        this.bookRepository.save(book);

        // vytvori vypujcku knihy
        Borrow b = new Borrow(
                (Long) this.borrowRepository.count(),
                new java.util.Date(),
                GlobalConfig.BORROW_DAY_COUNT,
                profile,
                book);
        this.borrowRepository.save(b);
    }

}
