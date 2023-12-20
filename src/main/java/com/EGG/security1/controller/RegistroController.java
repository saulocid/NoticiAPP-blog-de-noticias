package com.EGG.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            us.registrarUsuario(usuario, email, password, password2);
            return "redirect:/";
        } catch (Exception e) {
            modelo.addAttribute("error","No se pudo registrar");
            return "registro";
        }
    }
}
