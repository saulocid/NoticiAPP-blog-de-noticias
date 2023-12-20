package com.EGG.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.services.UsuarioServices;

@Controller
@RequestMapping("/inicio")
public class RegistroController {

    @Autowired
    private UsuarioServices us;

    @GetMapping("/registro")
    public String home() {
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String usuario, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap model) {
        try {
            us.registrarUsuario(usuario, email, password, password2, model);
            return "redirect:/";
        } catch (MyException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @PostMapping("/logear")
    public String logear(@RequestParam String email, @RequestParam String password, ModelMap model) {
        try {
            us.validarLogin(email, password, model);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
