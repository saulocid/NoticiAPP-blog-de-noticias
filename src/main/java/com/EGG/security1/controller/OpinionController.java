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
import org.springframework.web.bind.annotation.RequestParam;
import com.EGG.exceptions.MyException;
import com.EGG.security1.entities.Opinion;
import com.EGG.security1.services.OpinionServices;

@Controller
@RequestMapping("/opinion")
public class OpinionController {

    @Autowired
    private OpinionServices os;

    @GetMapping("/")
    public String opinion(Model model, ModelMap modelo) {
        if (!model.containsAttribute("opinion")) {
            model.addAttribute("opinion", new Opinion());
        }
        List<Opinion> opiniones = os.listarOpiniones();
        modelo.put("opiniones", opiniones);
        return "opinion";
    }

    @PostMapping("/envio")
    public String envio(ModelMap model, @ModelAttribute Opinion opinion) {
        try {
            os.crearOpinion(opinion.getId(), opinion.getEmail(), opinion.getMensaje());
            model.put("exito", "El mensaje se ha enviado exitosamente!");
            return "opinion";
        } catch (Exception e) {
            model.put("error", "Ha ocurrido un error y no se ha enviado el mensaje! Intente nuevamente.");
            return "opinion";
        }
    }

}
