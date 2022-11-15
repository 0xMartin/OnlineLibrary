package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.ADMIN)
public class AdminController {

  @Autowired
  protected UserService userService;

  /**
   * Zakladni view (notifikace, vypujcene knihy, odkazy na spravu uzivatelu a knih)
   * @return Nazev view
   */
  @GetMapping
  public String home(Model model) {
    try {
      List<User> users = userService.findUsersWithWaitingState();
      model.addAttribute("NOTIFICATIONS", users);
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/admin";
  }
}
