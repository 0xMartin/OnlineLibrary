package cz.utb.fai.LibraryApp;

public class AppRequestMapping {

  public static final String VIEW_PREFIX = "view";

  // kontroler pro autetizaci uzivatelu
  public static final String AUTH = "/auth";
  // kontroler pro: kataloge knih, ...
  public static final String HOME = "/home";
  // kontroler pro profil uzivatele
  public static final String PROFILE = "/profile";
  // kontroler pro admin sekci
  public static final String ADMIN = "/admin";
  // kontroler pro ziskavani obrazku knih z disku
  public static final String IMAGE = "/image";

  public static final String RESPONSE_SUCCESS = "SUCCESS";
  public static final String RESPONSE_INFO = "INFO";
  public static final String RESPONSE_ERROR = "ERROR";
}
