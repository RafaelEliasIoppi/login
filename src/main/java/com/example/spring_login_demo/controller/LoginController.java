package com.example.spring_login_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring_login_demo.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService; // Injeção do serviço

    @GetMapping({"/", "/login"})
    public String showLoginForm(@RequestParam(value = "message", required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("users", userService.listUsers());
        return "login"; // renderiza login.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String pwd,
                           Model model) {
        userService.registerUser(username, pwd);
        model.addAttribute("message", "Usuário registrado com sucesso!");
        return "redirect:/login"; // redireciona para login com mensagem
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String pwd,
                        Model model) {
        if (userService.validateLogin(username, pwd)) {
            // Se login for válido, redireciona para home
            return "redirect:/home";
        } else {
            model.addAttribute("message", "❌ Usuário ou senha incorretos!");
            model.addAttribute("users", userService.listUsers());
            return "login";
        }
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                        @RequestParam String pwd,
                        Model model) {
        if (userService.validateDelete(id, pwd)) {
            userService.deleteUser(id);
            model.addAttribute("message", "Usuário deletado com sucesso!");
        } else {
            model.addAttribute("message", "❌ Senha incorreta. Não foi possível deletar.");
        }
        model.addAttribute("users", userService.listUsers());
        return "login";
    }


    @GetMapping("/home")
    public String homePage() {
        return "home"; // renderiza home.html
    }
}
