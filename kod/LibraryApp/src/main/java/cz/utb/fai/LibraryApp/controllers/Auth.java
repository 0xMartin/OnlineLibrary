package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.GlobalConfig;
import cz.utb.fai.LibraryApp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.AUTH)
public class Auth {

  @GetMapping("/login")
  public String login() {
    return GlobalConfig.VIEW_PREFIX + "/login";
  }

  @GetMapping("/register")
  public String register() {
    return GlobalConfig.VIEW_PREFIX + "/register";
  }

  //@Secured({ ERole.Names.ADMIN, ERole.Names.LEADER, ERole.Names.ASSISTANT })
  @PostMapping("/")
  public String processRegister(@RequestBody User user) {
    return GlobalConfig.VIEW_PREFIX + "/register";
  }
}
