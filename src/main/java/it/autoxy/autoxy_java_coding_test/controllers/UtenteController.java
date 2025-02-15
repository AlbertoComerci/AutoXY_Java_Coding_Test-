package it.autoxy.autoxy_java_coding_test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

@RestController
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/public/utenti")
    public List<Utente> getAllUtenti() { 
        return utenteRepository.findAll(); 
    }

}
