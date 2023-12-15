package com.EGG.noticia.controllers;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.EGG.noticia.entities.Noticia;
import com.EGG.noticia.exceptions.MyException;
import com.EGG.noticia.services.NoticiaService;

@Controller
@RequestMapping("/admin")
public class PanelAdminController {

    @Autowired
    private NoticiaService ns;

    @GetMapping("/")
    public String admin(ModelMap model) {
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "panelAdmin";
    }

    @GetMapping("/lista")
    public String lista(ModelMap model) {
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaList";
    }

    @GetMapping("/nueva")
    public String nueva(ModelMap model) {
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaNew";
    }

    @PostMapping("/nueva")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap model) {
        try {
            ns.crearNoticia(titulo, cuerpo);
        } catch (MyException e) {
            model.put("error", e.getMessage());
        } finally {
            List<Noticia> noticias = ns.listarNoticias();
            Collections.reverse(noticias);
            model.addAttribute("noticias", noticias);
            int fecha = ns.fecha();
            model.addAttribute("fecha", fecha);
            return "noticiaList";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap model) {
        model.addAttribute("noticia", ns.noticia(id));
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaChange";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String titulo, @RequestParam String cuerpo, ModelMap model) {
        try {
            ns.modificarNoticia(id, titulo, cuerpo);
            List<Noticia> noticias = ns.listarNoticias();
            Collections.reverse(noticias);
            model.addAttribute("noticias", noticias);
            int fecha = ns.fecha();
            model.addAttribute("fecha", fecha);
            return "noticiaList";
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            model.addAttribute("noticia", ns.noticia(id));
            int fecha = ns.fecha();
            model.addAttribute("fecha", fecha);
            return "noticiaChange";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, ModelMap model) throws MyException {
        ns.eliminarNoticia(id);
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaList";
    }

}
