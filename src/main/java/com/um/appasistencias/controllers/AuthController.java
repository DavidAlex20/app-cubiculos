package com.um.appasistencias.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
public class AuthController {
    @GetMapping
    public String indexRedirect() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginForm(@Param("error") String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }
}
