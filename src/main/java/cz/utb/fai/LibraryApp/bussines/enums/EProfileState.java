package cz.utb.fai.LibraryApp.bussines.enums;

/**
 * Stav uzivatelskeho profilu
 */
public enum EProfileState {
  /**
   * Profil stale ceka na potvrzeni
   */
  WAITING,
  /**
   * Nepotvrzeny profil
   */
  NOT_CONFIRMED,

  /**
   * Potvrzeny profil
   */
  CONFIRMED,

  /**
   * Profil zabanovany
   */
  BANNED;

  public static EProfileState fromString(String str) {
    switch (str.toUpperCase()) {
      case "WAITING":
        return EProfileState.WAITING;
      case "NOT_CONFIRMED":
        return EProfileState.NOT_CONFIRMED;
      case "CONFIRMED":
        return EProfileState.CONFIRMED;
      case "BANNED":
        return EProfileState.BANNED;
    }
    return EProfileState.NOT_CONFIRMED;
  }
}
