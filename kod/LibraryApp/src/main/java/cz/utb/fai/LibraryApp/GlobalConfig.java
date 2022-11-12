package cz.utb.fai.LibraryApp;

public class GlobalConfig {

    /**
     * maximalni pocet knih, ktere si uzivatel muze vypujcit (zmena nebudou
     * provedeny na jiz vypujcene knihy)
     * [POCET KNIH]
     */
    public static long MAX_BORROWED_BOOKS = 6;

    /**
     * maximalni pocet adminu (min 1)
     * [POCET UZIVATELU]
     */
    public static long MAX_ADMIN_COUNT = 1;

    /**
     * douba, na kterou si uzivatel pujcuje knihu (po uplynuti se kniha automaticky
     * vrati)
     * [DNY]
     */
    public static long BORROW_DAY_COUNT = 6;

}
