package com.EGG.security1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.EGG.security1.entities.Noticia;
import com.EGG.security1.services.NoticiaServices;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private NoticiaServices ns;
    
    // creamos la vista principal de la app de noticias
    @GetMapping("/")
    public String home(ModelMap model){

        //traemos la lista para poder mostrarla en pantalla con las llaves en el modelmap
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "inicio";
    }

    // creamos el postmapping para el botón de "cerrar sesión"
    @PostMapping("/logout")
    public String cerrarSesion(){
        return "redirect:/";
    }

}