package com.EGG.security1.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.EGG.security1.entities.Noticia;
import com.EGG.security1.entities.Opinion;
import com.EGG.security1.entities.Usuario;
import com.EGG.security1.enums.Rol;
import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.services.NoticiaServices;
import com.EGG.security1.services.OpinionServices;
import com.EGG.security1.services.UsuarioServices;

@Controller
@RequestMapping("/admin")
public class PanelAdmController {

    @Autowired
    private NoticiaServices ns;

    @Autowired
    private OpinionServices os;

    @Autowired
    private UsuarioServices us;

    @GetMapping("/")
    public String admin(ModelMap model) {
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "panelAdmin";
    }

    @GetMapping("/lista")
    public String lista(ModelMap model) {
        noticiaList(model);
        return "noticiaList";
    }

    @GetMapping("/opiniones")
    public String opiciones(ModelMap model) {
        List<Opinion> ops = os.listarOpiniones();
        model.addAttribute("opiniones", ops);
        return "opinionList";
    }

    @GetMapping("/nueva")
    @PreAuthorize("hasRole('JOURNAL')")
    public String nueva(ModelMap model) {
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaNew";
    }

    @PostMapping("/nueva")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap model) {
        try {
            ns.crearNoticia(titulo, cuerpo);
            return "redirect:/admin/lista";
        } catch (MyException e) {
            model.addAttribute("error", e.getMessage());
            noticiaList(model);
            return "noticiaList";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        model.addAttribute("noticia", ns.noticia(id));
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
        return "noticiaChange";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, @RequestParam String cuerpo, ModelMap model) {
        try {
            ns.modificarNoticia(id, titulo, cuerpo);
            return "redirect:/admin/lista";
        } catch (MyException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/admin/modificar/{id}";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap model) throws MyException {
        ns.eliminarNoticia(id);
        return "redirect:/admin/lista";
    }

    @PostMapping("/eliminarOpinion/{id}")
    public String eliminarOps(@PathVariable String id) throws MyException {
        os.eliminarOpinion(id);
        return "redirect:/admin/opiniones";
    }

    @GetMapping("/usuarioList")
    @PreAuthorize("hasRole('MODERATOR')")
    public String usuarioList(ModelMap model) {
        List<Usuario> usuarios = us.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarioList";
    }

    @PostMapping("/cambiarRol/{id}")
    public String cambiarRol(@PathVariable String id, @RequestParam Rol rol) {
        try {
            us.cambiarRol(id, rol);
            return "redirect:/admin/usuarioList";
        } catch (Exception e) {
            return "redirect:/admin/usuarioList";
        }
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id) throws MyException {
        us.eliminarUsuario(id);
        return "redirect:/admin/usuarioList";
    }

    public void noticiaList(ModelMap model) {
        List<Noticia> noticias = ns.listarNoticias();
        Collections.reverse(noticias);
        model.addAttribute("noticias", noticias);
        int fecha = ns.fecha();
        model.addAttribute("fecha", fecha);
    }

}
