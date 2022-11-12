package cz.utb.fai.LibraryApp.models;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Data;

@Document("Books")
@Data
public class Borrow {

    @Id
    private long id;

    /**
     * Za kolik dni bude vypujcka knihy expirovat (od data
     * vytvoreni vypujcky)
     */
    private long expiration;

    /**
     * Datum vypujceni knihy
     */
    private Date date;

    /**
     * Uzivatel, ktery si knihu pujcil
     */
    @DocumentReference(lazy = false)
    private User user;

    /**
     * Knihy, kterou si uzivatel vypujcil
     */
    @DocumentReference(lazy = false)
    private Book book;

    /**
     * Vytvori instanci reprezentujici vypujceni knihy
     * 
     * @param id         ID vypujceni
     * @param expiration Za kolik dni bude vypujcka knihy expirovat (od data
     *                   vytvoreni vypujcky)
     * @param date       Datum vytvoreni vypujcky knihy
     * @param user       Uzivatel, ktery si knihu vypujcil
     * @param book       Knihy, kterou si uzivatel vypujcil
     */
    public Borrow(long id, long expiration, Date date, User user, Book book) {
        this.id = id;
        this.expiration = expiration;
        this.date = date;
        this.user = user;
        this.book = book;
    }

}
