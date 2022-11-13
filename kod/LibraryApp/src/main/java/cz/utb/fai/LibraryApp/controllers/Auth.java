package cz.utb.fai.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.utb.fai.LibraryApp.AppRequestMapping;

@Controller
@RequestMapping(value = AppRequestMapping.AUTH)
public class Auth {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/process-login")
    public String processLogin() {
        
        return "login";
    }

}
