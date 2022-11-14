package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.GlobalConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.HOME)
public class HomeController {

  @GetMapping
  public String home() {
    return GlobalConfig.VIEW_PREFIX + "/index";
  }

  @GetMapping("/about")
  public String about() {
    return GlobalConfig.VIEW_PREFIX + "/about";
  }
  
}
