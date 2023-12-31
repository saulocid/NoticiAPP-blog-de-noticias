package com.EGG.security1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.EGG.security1.entities.Opinion;
import com.EGG.security1.services.OpinionServices;

@Controller
@RequestMapping("/opinion")
public class OpinionController {

    @Autowired
    private OpinionServices os;

    @GetMapping("/")
    public String opinion(Model mo, ModelMap model) {
        modelos(mo, model);
        return "opinion";
    }

    @PostMapping("/envio")
    public String envio(Model mo, ModelMap model, @ModelAttribute Opinion opinion) {
        try {
            os.crearOpinion(opinion.getId(), opinion.getEmail(), opinion.getMensaje());
            model.addAttribute("exito", "El mensaje se ha enviado exitosamente!");
            modelos(mo, model);
            return "opinion";
        } catch (Exception e) {
            model.addAttribute("error", "Ha ocurrido un error y no se ha enviado el mensaje! Intente nuevamente.");
            modelos(mo, model);
            return "opinion";
        }
    }

    public void modelos(Model mo, ModelMap model) {
        mo.addAttribute("opinion", new Opinion());
        List<Opinion> opiniones = os.listarOpiniones();
        model.addAttribute("opiniones", opiniones);
    }

}
