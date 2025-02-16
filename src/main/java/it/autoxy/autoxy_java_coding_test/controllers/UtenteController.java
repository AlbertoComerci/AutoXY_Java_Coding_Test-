package it.autoxy.autoxy_java_coding_test.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.services.UtenteServiceImpl;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteServiceImpl utenteServiceImpl;

    @GetMapping
    public ResponseEntity<List<UtenteDto>> getAllUtenti() {
        try {
            List<UtenteDto> utentiDto = utenteServiceImpl.getAll();
            return ResponseEntity.ok(utentiDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UtenteDto utenteDto) {
        try {
            Utente utente = utenteServiceImpl.saveUtente(utenteDto);
            UtenteDto responseDto = utenteServiceImpl.convertToDto(utente);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella registrazione dell'utente");
        }
    }

}
