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

@Controller
@RequestMapping(value = AppRequestMapping.AUTH)
public class AuthController {

  @Autowired
  protected UserService userService;

  /**
   * View pro login uzivatele
   * 
   * @return Nazev view
   */
  @GetMapping("/login")
  public String login() {
    return AppRequestMapping.VIEW_PREFIX + "/auth/login";
  }

  /**
   * View pro registraci uzivatele
   * 
   * @return Nazev view
   */
  @GetMapping("/register")
  public String register() {
    return AppRequestMapping.VIEW_PREFIX + "/auth/register";
  }

  /**
   * View pro registraci uzivatele
   * 
   * @param model ViewModel
   * @param user  User
   * @return Nazev view
   */
  @PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded")
  public String processRegister(Model model, User user) {
    try {
      userService.createUser(user);
      model.addAttribute(AppRequestMapping.RESPONSE_SUCCESS, "Registration was successful");
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/auth/register";
  }
}
