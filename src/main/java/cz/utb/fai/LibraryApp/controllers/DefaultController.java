package cz.utb.fai.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import cz.utb.fai.LibraryApp.AppRequestMapping;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

  /**
   * Presmerovani na home kontroler
   * 
   * @return
   */
  @GetMapping
  public RedirectView login() {
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl(AppRequestMapping.HOME);
    return redirectView;
  }

}
