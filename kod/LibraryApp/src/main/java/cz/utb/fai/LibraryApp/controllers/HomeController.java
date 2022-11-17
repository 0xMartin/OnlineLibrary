package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.HOME)
public class HomeController {

  /**
   * View s katalogem knih (homepage)
   * @return Nazev view
   */
  @GetMapping
  public String home() {
    return AppRequestMapping.VIEW_PREFIX + "/home/index";
  }

  /**
   * View s informacemi of aplikaci
   * @return Nazev view
   */
  @GetMapping("/about")
  public String about() {
    return AppRequestMapping.VIEW_PREFIX + "/home/about";
  }
}
