package com.egg.security1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(ModelMap model) {
        
        return "index";
    }

}
