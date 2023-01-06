package cz.utb.fai.LibraryApp.bussines.enums;

/**
 * Uzivatelske role
 */
public enum ERole {
  /**
   * Role zakaznika
   */
  CUSTOMER,

  /**
   * Role knihovnika
   */
  LIBRARIAN;

  public static class Names {

    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String LIBRARIAN = "ROLE_LIBRARIAN";
  }

  public static ERole fromString(String str) {
    switch (str.toUpperCase()) {
      case "CUSTOMER":
        return ERole.CUSTOMER;
      case "LIBRARIAN":
        return ERole.LIBRARIAN;
    }
    return ERole.CUSTOMER;
  }
}
