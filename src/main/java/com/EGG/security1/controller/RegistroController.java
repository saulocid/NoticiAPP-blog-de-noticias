package com.EGG.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
    @RequestMapping("/inicio")
    public class RegistroController {
    
    @GetMapping("/registro")
    public String home(ModelMap model){
       
        return "registro";
    }    
}


    

