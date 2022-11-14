package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = AppRequestMapping.PROFILE)
public class ProfileController {

  @Autowired
  protected UserService userService;

  /**
   * View s profilem uzivatele (parametry uzivatele + historie vsech vypujcek)
   * @param model ViewModel
   * @param page Cislo stranky s vypujckama knizek (stranky po 8 zaznamech)
   * @param borrowedOnly Zobrazovat jen aktualne vypujcene knihy v seznamu
   * @return Nazev View
   */
  @GetMapping
  public String profile(
    Model model,
    @RequestParam(required = false) Long page,
    @RequestParam(required = false) Boolean borrowedOnly
  ) {
    User u = this.userService.profile();
    model.addAttribute("USER", u);
    return AppRequestMapping.VIEW_PREFIX + "/profile";
  }

  /**
   * View pro editace parametru uzivatele
   * @return Nazev View
   */
  @GetMapping("edit")
  public String edit() {
    return AppRequestMapping.VIEW_PREFIX + "/edit_profile";
  }

  /**
   * View pro zmenu hesla
   * @return Nazev View
   */
  @GetMapping("changePassword")
  public String changePassword() {
    return AppRequestMapping.VIEW_PREFIX + "/change_password";
  }
}
