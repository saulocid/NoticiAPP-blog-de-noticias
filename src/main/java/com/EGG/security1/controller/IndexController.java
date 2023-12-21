package com.EGG.security1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.EGG.security1.entities.Noticia;
import com.EGG.security1.entities.User;
import com.EGG.security1.services.NoticiaServices;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/")
    public String index(ModelMap model) {
        notiLista(model);
        return "inicio";
    }

    // creamos la vista principal de la app de noticias
    @GetMapping("/inicio")
    public String home(ModelMap model, HttpSession sesion) {

        // traemos usuario y validamos si es admin

        User logeado = (User) sesion.getAttribute("usuarioSesion");
        if (logeado.getRol().toString().equals("ADMIN") || logeado.getRol().toString().equals("JOURNAL")) {
            return "redirect:/admin/";
        }

        // traemos la lista para poder mostrarla en pantalla con las llaves en el modelmap
        model.addAttribute("sesion", sesion);
        notiLista(model);
        return "index";
    }

    // creamos el postmapping para el botón de "cerrar sesión"
    @PostMapping("/logout")
    public String cerrarSesion() {
        return "redirect:/";
    }

    public void notiLista(ModelMap model){
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
    }

}