package cz.utb.fai.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.utb.fai.LibraryApp.AppRequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.HOME)
public class Home {
    
    @GetMapping()
    public String home() {
        return "index";
    }

}
