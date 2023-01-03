package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.BookService;
import cz.utb.fai.LibraryApp.bussines.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = AppRequestMapping.HOME)
public class HomeController {

  @Autowired
  protected UserService userService;

  @Autowired
  protected BookService bookService;

  /**
   * View s katalogem knih (homepage)
   * 
   * @param model ViewModel
   * @return Nazev view
   */
  @GetMapping
  public String home(Model model) {
    model.addAttribute("BOOKS", this.bookService.books());
    return AppRequestMapping.VIEW_PREFIX + "/home/index";
  }

  /**
   * View s informacemi of aplikaci
   * 
   * @param model ViewModel
   * @param id    ID knihy, ktera bude zobrazena
   * @return Nazev view
   */
  @GetMapping("/info")
  public String info(Model model, @RequestParam Long id) {
    try {
      model.addAttribute("USER", this.userService.profile());
      model.addAttribute("BOOK", this.bookService.findBook(id));
    } catch (Exception e) {
      model.addAttribute(AppRequestMapping.RESPONSE_ERROR, e.getMessage());
    }
    return AppRequestMapping.VIEW_PREFIX + "/home/info";
  }

  /**
   * View s informacemi o webove aplikaci
   * 
   * @return Nazev view
   */
  @GetMapping("/about")
  public String about() {
    return AppRequestMapping.VIEW_PREFIX + "/home/about";
  }
}
