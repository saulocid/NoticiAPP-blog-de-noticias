package com.EGG.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.services.UsuarioServices;

@Controller
@RequestMapping("/sesion")
public class RegistroController {

    @Autowired
    private UsuarioServices us;

    @GetMapping("/registro")
    public String home() {
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam MultipartFile archivo, @RequestParam String usuario, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap model) {
        try {
            us.registrarUsuario(archivo, usuario, email, password, password2);
            model.addAttribute("exito", "Usuario creado con éxito. Ahora ingresa.");
            return "registro";
        } catch (MyException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @PostMapping("/logear")
    public String logearUsuario(@RequestParam String email, @RequestParam String password, ModelMap model) {
        try {
            us.validarLogin(email, password);
            return "redirect:/inicio";
        } catch (MyException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }

    }
}
