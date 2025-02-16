package it.autoxy.autoxy_java_coding_test.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import it.autoxy.autoxy_java_coding_test.services.UtenteService;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteService utenteService;

    
    @GetMapping 
    public ResponseEntity<List<UtenteDto>> getAllUtenti() {
        try {
            List<Utente> utenti = utenteRepository.findAll();
            List<UtenteDto> utentiDto = utenti.stream()
                .map(utente -> {
                    UtenteDto dto = new UtenteDto();
                    dto.setId(utente.getId());
                    dto.setNome(utente.getUsername().split(" ")[0]);
                    dto.setCognome(utente.getUsername().split(" ")[1]);
                    dto.setEmail(utente.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(utentiDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UtenteDto> register(@RequestBody UtenteDto utenteDto) {
        // Salva l'utente nel database
        Utente utente = utenteService.saveUtente(utenteDto);

        // Converte l'utente salvato in DTO per la risposta
        UtenteDto responseDto = new UtenteDto();
        responseDto.setId(utente.getId());
        responseDto.setNome(utente.getUsername().split(" ")[0]); // Estrae il nome dall'username
        responseDto.setCognome(utente.getUsername().split(" ")[1]); // Estrae il cognome dall'username
        responseDto.setEmail(utente.getEmail());

        // Restituisce la risposta con lo status 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
