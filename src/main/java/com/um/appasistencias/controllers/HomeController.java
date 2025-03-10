package com.um.appasistencias.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;

@Controller
@RequestMapping("/dashboard")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String index(@AuthenticationPrincipal Usuarios user, Model model){
        
        DatosVista datosVista = new DatosVista(user, "index", "Menú", false);
        log.info(datosVista.toString());
        model.addAttribute("datosVista", datosVista);
        return "index";
    }
}
