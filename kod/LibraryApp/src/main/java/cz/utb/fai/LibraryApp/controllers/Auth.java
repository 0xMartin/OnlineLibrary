package cz.utb.fai.LibraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")  
public class Auth {

    @RequestMapping("/login")
    public String login()  
    {  
        return "index";  
    }  
    
    @RequestMapping("/register")
    public String register()  
    {  
        return "register";  
    }    

}
