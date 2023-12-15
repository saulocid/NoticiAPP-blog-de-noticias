package com.EGG.noticia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.EGG.noticia.entities.Noticia;
import com.EGG.noticia.services.NoticiaService;

@Controller
@RequestMapping("/cuerpo")
public class NoticiaController {

    @Autowired
    private NoticiaService ns;

    @GetMapping("/{id}")
    public String cuerpo(@PathVariable Long id, ModelMap model){
        Noticia noticia = ns.listarNoticia(id);
        model.addAttribute("noticia", noticia);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticia";
    }

}
