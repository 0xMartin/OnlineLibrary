package cz.utb.fai.LibraryApp.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Data;

/**
 * Trida reprezentujici uzivatele
 */
@Document("Users")
@Data
public class User {

    @Id
    private long id;

    /**
     * Jmeno uzivatele
     */
    private String name;

    /**
     * Prijmeni uzivatele
     */
    private String surname;

    /**
     * Rodne cislo uzivatele
     */
    private String personaID;

    /**
     * Adresa uzivatele
     */
    private String address;

    /**
     * Uzivatelske jmeno (pro prihlasovani)
     */
    private String username;

    /**
     * Heslo uzivatele (hash)
     */
    private String password;

    /**
     * Stav profilu uzivatele, ve kterem se aktualne nachazi
     */
    @DocumentReference(lazy = false)
    private ProfileState state;

    /**
     * Role uzivatele
     */
    @DocumentReference(lazy = false)
    private Role role;

    /**
     * Seznam vsech vypujcek knih
     */
    @DocumentReference(lazy = true)
    private List<Borrow> borrows;

    /**
     * Vytvori instanci uzivatele
     * 
     * @param id        ID uzivatele
     * @param name      Jmeno uzivatele
     * @param surname   Prijmeni uzivatele
     * @param personaID Rodne cislo uzivatele
     * @param address   Adresa uzivatele
     * @param username  Uzivatelske jmeno
     * @param password  Heslo uzivatele
     * @param state     Stav profilu (vice v ./enums/EProfileState.java)
     * @param role      Role uzivatle (vice v ./enums/ERole.java)
     */
    public User(long id, String name, String surname,
            String personaID, String address,
            String username, String password,
            ProfileState state, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personaID = personaID;
        this.address = address;
        this.username = username;
        this.password = password;
        this.state = state;
        this.role = role;
    }

}
