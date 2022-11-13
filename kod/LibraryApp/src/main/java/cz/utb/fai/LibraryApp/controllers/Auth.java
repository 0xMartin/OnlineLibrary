package cz.utb.fai.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.utb.fai.LibraryApp.AppRequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.AUTH)
public class Auth {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String processLogin() {
        
        return "register";
    }

}
