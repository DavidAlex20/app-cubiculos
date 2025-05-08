package com.um.appasistencias.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;

@Controller
@RequestMapping("/calendario")
public class CalendarioController {
    private static final Logger log = LoggerFactory.getLogger(CalendarioController.class);

    @GetMapping
    public String index(@AuthenticationPrincipal Usuarios user, Model model) {
        DatosVista datosVista = new DatosVista(user, "calendario", "Calendario", false);
        log.info(datosVista.toString());
        model.addAttribute("datosVista", datosVista);
        
        return "calendario";
    }
    
}
