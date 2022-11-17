package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
import cz.utb.fai.LibraryApp.bussines.services.BookService;
import cz.utb.fai.LibraryApp.bussines.services.BorrowService;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = AppRequestMapping.ADMIN)
public class AdminController {

  @Autowired
  protected UserService userService;

  @Autowired
  protected BookService bookService;

  @Autowired
  protected BorrowService borrowService;

  /**
   * Zakladni view (notifikace a jejich potvrzovani, vypujcene knihy, odkazy na spravu uzivatelu a knih)
   * @param username Uzivatelske jmeno uzivatele jehoz ucet bude potvrzen/zamitnut
   * @param confirmed True:Potvrzen/False:Zamitrnut
   * @return Nazev view
   */
  @GetMapping
  public String home(
    Model model,
    @RequestParam(required = false) String username,
    @RequestParam(required = false) Boolean confirmed
  ) {
    try {
      // pokud je specifikovano uzivatelske jmeno a stav potvrzeni => provede se zmena stavu uctu
      if (username != null && confirmed != null) {
        this.userService.setProfileState(
            username,
            confirmed ? EProfileState.CONFIRMED : EProfileState.NOT_CONFIRMED
          );
      }
      // notifikace -> uzivatele, kteri cekaji na schvaleni
      List<User> users = userService.findUsersWithWaitingState();
      model.addAttribute("NOTIFICATIONS", users);
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/admin/admin";
  }

  /**
   *
   * @param model ViewModel
   * @return Nazev view
   */
  @GetMapping("/users")
  public String users(Model model) {
    List<User> users = this.userService.users();
    model.addAttribute("USERS", users);
    return AppRequestMapping.VIEW_PREFIX + "/admin/users";
  }

  /**
   * Zobrazi view pro uzivatele a zaroven odstrani specifikovaneho uzivatele
   * @param model ViewModel
   * @param username Uzivatelske jmeno uzivatele, ktery bude odstranen
   * @return Nazev view
   */
  @GetMapping("/usersDelete")
  public String usersDelete(Model model, @RequestParam String username) {
    try {
      this.userService.removeUser(username);
      model.addAttribute(
        AppRequestMapping.RESPONSE_SUCCESS,
        String.format("User '%s' successfully removed", username)
      );
    } catch (Exception e) {
      List<User> users = this.userService.users();
      model.addAttribute("USERS", users);
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }

    List<User> users = this.userService.users();
    model.addAttribute("USERS", users);

    return AppRequestMapping.VIEW_PREFIX + "/admin/users";
  }

  /**
   * Zobrazi view pro uzivatele a zaroven zabanuje/odbanuje specifikovaneho uzivatele
   * @param model ViewModel
   * @param username Uzivatelske jmeno uzivatele, ktery bude zabanovan
   * @param ban True -> zabanuje, False -> odbanuje
   * @return Nazev view
   */
  @GetMapping("/banUser")
  public String banUser(
    Model model,
    @RequestParam String username,
    boolean ban
  ) {
    try {
      this.userService.setProfileState(
          username,
          ban ? EProfileState.BANNED : EProfileState.WAITING
        );
      model.addAttribute(
        AppRequestMapping.RESPONSE_SUCCESS,
        String.format(
          (
            ban
              ? "User '%s' successfully banned"
              : "User '%s' successfully unbanned"
          ),
          username
        )
      );
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    List<User> users = this.userService.users();
    model.addAttribute("USERS", users);

    return AppRequestMapping.VIEW_PREFIX + "/admin/users";
  }

  /**
   *
   * @param model
   * @return
   */
  @GetMapping("/books")
  public String books(Model model) {
    return AppRequestMapping.VIEW_PREFIX + "/admin/users";
  }
}
