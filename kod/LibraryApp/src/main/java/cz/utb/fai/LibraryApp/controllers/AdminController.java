package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.enums.EProfileState;
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
    return AppRequestMapping.VIEW_PREFIX + "/admin";
  }
}
