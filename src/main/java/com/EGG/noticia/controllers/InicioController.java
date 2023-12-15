package com.EGG.noticia.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.EGG.noticia.entities.Noticia;
import com.EGG.noticia.services.NoticiaService;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    private NoticiaService ns;
    
    @GetMapping("/")
    public String home(ModelMap model){
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "inicio";
    }

}
