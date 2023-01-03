package cz.utb.fai.LibraryApp;

public class GlobalConfig {

  /**
   * douba, na kterou si uzivatel pujcuje knihu (po uplynuti se kniha automaticky
   * vrati)
   * [DNY]
   */
  public static long BORROW_DAY_COUNT = 6;

  /**
   * maximalni pocet knih, ktere si uzivatel muze vypujcit (zmena nebudou
   * provedeny na jiz vypujcene knihy)
   * [POCET KNIH]
   */
  public static long MAX_BORROWED_BOOKS = 6;

  /**
   * minimalni delka hesla
   * [pocet znaku]
   */
  public static long MIN_PASSWORD_LENGTH = 8;

  /**
   * Nazev adresare do ktereho se budou uploadovat obrazky (cesta k adresari se
   * bere relativne k .jar webove app)
   */
  public static String IMAGE_UPLOAD_DIR = "images";

}
