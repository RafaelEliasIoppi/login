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
        model.addAttribute("users", userService.listUsers()); // lista usuários na tela
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String pwd,
                           Model model) {
        userService.registerUser(username, pwd);
        model.addAttribute("message", "Usuário registrado com sucesso!");
        model.addAttribute("users", userService.listUsers());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String pwd,
                        Model model) {
        if (userService.validateLogin(username, pwd)) {
            model.addAttribute("message", "✅ Login bem-sucedido!");
        } else {
            model.addAttribute("message", "❌ Usuário ou senha incorretos!");
        }
        model.addAttribute("users", userService.listUsers());
        return "login";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("message", "Usuário deletado com sucesso!");
        model.addAttribute("users", userService.listUsers());
        return "login";
    }
}
