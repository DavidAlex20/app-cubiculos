package com.um.appasistencias.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.services.CubiculosService;
import com.um.appasistencias.services.EventosService;
import com.um.appasistencias.services.PaselistaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/paselista")
public class PaselistaController {
    private static final Logger log = LoggerFactory.getLogger(EventosController.class);
    @Autowired private PaselistaService paselistaService;
    @Autowired private CubiculosService cubiculosService;
    @Autowired private EventosService eventosService;

    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model) {
        
        return "admin/paselista";
    }
    
}
