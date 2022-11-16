package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    try {
      User u = this.userService.profile();
      model.addAttribute("USER", u);
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/profile";
  }

  /**
   * View pro editace parametru uzivatele
   * @param model ViewModel
   * @return Nazev View
   */
  @GetMapping("edit")
  public String edit(Model model) {
    try {
      User u = this.userService.profile();
      model.addAttribute("USER", u);
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/edit_profile";
  }

  /**
   * Provede zmeny v porfilu
   * @param model ViewModel
   * @param user Nove paramtery profilu uzivatele
   * @return Nazev View
   */
  @PostMapping(path = "edit", consumes = "application/x-www-form-urlencoded")
  public String edit(Model model, User user) {
    try {
      User u = this.userService.profile();
      model.addAttribute("USER", u);

      User n = this.userService.editUser(u.getUsername(), user);
      model.addAttribute("USER", n);


      model.addAttribute(
        AppRequestMapping.RESPONSE_SUCCESS,
        "Changed successfully"
      );
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
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

  /**
   * Zmeni heslo uzivatele
   * @param model ViewModel
   * @param currentPass Aktualni heslo
   * @param newPass Nove heslo
   * @return Nazev View
   */
  @PostMapping(
    path = "changePassword",
    consumes = "application/x-www-form-urlencoded"
  )
  public String changePassword(
    Model model,
    String currentPass,
    String newPass
  ) {
    try {
      this.userService.changePassword(currentPass, newPass);
      model.addAttribute(
        AppRequestMapping.RESPONSE_SUCCESS,
        "Password changed successfully"
      );
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/change_password";
  }
}
