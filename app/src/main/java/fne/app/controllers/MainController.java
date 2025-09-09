package fne.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Devuelve el nombre de la plantilla HTML (login.html)
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/votar";
    }

    @GetMapping("/logout")
    public String logout() {
        // Spring Security se encargará de destruir la sesión automáticamente
        // cuando se accede a este endpoint.
        return "redirect:/login?logout";
    }
}
