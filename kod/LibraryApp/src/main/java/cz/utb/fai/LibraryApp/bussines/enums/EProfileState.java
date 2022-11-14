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
  BANNED,
}
