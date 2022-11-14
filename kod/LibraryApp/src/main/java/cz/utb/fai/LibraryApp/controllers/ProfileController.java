package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.PROFILE)
public class ProfileController {

    @Autowired
    protected UserService userService;

  @GetMapping
  public String profile(Model model) {
    User u = this.userService.profile();
    model.addAttribute("USER_ID", u.getId());
    model.addAttribute("USER_NAME", u.getName());
    model.addAttribute("USER_SURNAME", u.getSurname());
    model.addAttribute("USER_PERSONALID", u.getPersonaID());
    model.addAttribute("USER_ADDRESS", u.getAddress());
    model.addAttribute("USER_USERNAME", u.getUsername());
    return GlobalConfig.VIEW_PREFIX + "/profile";
  }

  @GetMapping("edit")
  public String edit() {
    return GlobalConfig.VIEW_PREFIX + "/edit_profile";
  }

  @GetMapping("changePassword")
  public String changePassword() {
    return GlobalConfig.VIEW_PREFIX + "/change_password";
  }

}
