package com.EGG.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.EGG.security1.entities.Noticia;
import com.EGG.security1.services.NoticiaServices;

@Controller
@RequestMapping("/cuerpo")
public class NoticiaController {

    @Autowired
    private NoticiaServices ns;

    @GetMapping("/{id}")
    public String cuerpo(@PathVariable String id, ModelMap model){
        Noticia noticia = ns.listarNoticia(id);
        model.addAttribute("noticia", noticia);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticia";
    }

}
