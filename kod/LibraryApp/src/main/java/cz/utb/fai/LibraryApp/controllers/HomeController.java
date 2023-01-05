package cz.utb.fai.LibraryApp.controllers;

import cz.utb.fai.LibraryApp.AppRequestMapping;
import cz.utb.fai.LibraryApp.bussines.services.BookService;
import cz.utb.fai.LibraryApp.bussines.services.UserService;
import cz.utb.fai.LibraryApp.model.Book;

import java.util.List;

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
   * @param model             ViewModel
   * @param name              - Jmeno knihy (pro filtrovani dat)
   * @param author            - Autor knihy (pro filtrovani dat)
   * @param yearOfPublication - Rok vydani (pro filtrovani dat)
   * @param sortedBy          - Radi podle: 0 - name, 1 - author, ... (pro
   *                          filtrovani dat)
   * @return Nazev view
   */
  @GetMapping
  public String home(Model model,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String author,
      @RequestParam(required = false) String yearOfPublication,
      @RequestParam(required = false) Integer sortedBy,
      @RequestParam(required = false) Boolean sortingASC,
      @RequestParam(required = false) Integer page) {

    if (name == null)
      name = "";
    if (author == null)
      author = "";
    if (yearOfPublication == null)
      yearOfPublication = "";
    if (sortedBy == null)
      sortedBy = -1;
    if (sortingASC == null)
      sortingASC = false;

    // vyhledani knih odpovidajicich zvolenemu filtru
    List<Book> books = this.bookService.findBooks(
        name, author, yearOfPublication, sortedBy, sortingASC);
    model.addAttribute("BOOKS", books);

    // aktualni konfigurace filtru
    model.addAttribute("FILTER_NAME", name);
    model.addAttribute("FILTER_AUTHOR", author);
    model.addAttribute("FILTER_YEAR", yearOfPublication);
    model.addAttribute("FILTER_SORTED", sortedBy);
    model.addAttribute("FILTER_ASC", sortingASC);

    model.addAttribute("PAGE_CURRENT", page);
    model.addAttribute("PAGE_COUNT", 3);

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
      model.addAttribute("BOOK", this.bookService.findBook(id));
      try {
        model.addAttribute("USER", this.userService.profile());
      } catch (Exception e) {
      }
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
