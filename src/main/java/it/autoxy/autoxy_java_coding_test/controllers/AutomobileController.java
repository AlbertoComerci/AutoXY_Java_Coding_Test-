package it.autoxy.autoxy_java_coding_test.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.RequestBody;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileRequestDto;
import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.services.AutomobileService;

@RestController
@RequestMapping("/api/automobili")
public class AutomobileController {

    @Autowired
    private AutomobileService automobileService;

    @Operation(summary = "Ottieni tutte le automobili", description = "Restituisce una lista di automobili")
    @ApiResponse(responseCode = "200", description = "Automobili trovate con successo")
    @GetMapping
    public ResponseEntity<List<AutomobileDto>> getAllAutomobili() {
        List<AutomobileDto> automobili = automobileService.readAll();
        return ResponseEntity.ok(automobili);
    }

    @Operation(summary = "Mostra una singola automobile", description = "Restituisce un'automobile specifica")
    @ApiResponse(responseCode = "200", description = "Automobile trovate con successo")
    @GetMapping("/{id}")
    public ResponseEntity<AutomobileDto> getAutomobileById(@PathVariable Long id) {
        AutomobileDto automobile = automobileService.read(id);
        return ResponseEntity.ok(automobile);
    }

    @PostMapping
    public ResponseEntity<AutomobileDto> createAutomobile(@RequestBody AutomobileRequestDto dto) {
        System.out.println("Automobile ricevuta: " + dto);
        AutomobileDto createdAutomobile = automobileService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAutomobile);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("@automobileService.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<AutomobileDto> updateAutomobile(@PathVariable Long id, @RequestBody AutomobileRequestDto dto) {
        AutomobileDto updated = automobileService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("@automobileService.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Void> deleteAutomobile(@PathVariable Long id) {
        automobileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Query personalizzate

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByUtente(@PathVariable Long utenteId) {
        Utente utente = new Utente();
        utente.setId(utenteId);
        List<AutomobileDto> automobili = automobileService.findByUtente(utente);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByMarca(@PathVariable Long marcaId) {
        Marca marca = new Marca();
        marca.setId(marcaId);
        List<AutomobileDto> automobili = automobileService.findByMarca(marca);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/modello/{modelloId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByModello(@PathVariable Long modelloId) {
        Modello modello = new Modello();
        modello.setId(modelloId);
        List<AutomobileDto> automobili = automobileService.findByModello(modello);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/regione/{regioneId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByRegione(@PathVariable Long regioneId) {
        Regione regione = new Regione();
        regione.setId(regioneId);
        List<AutomobileDto> automobili = automobileService.findByRegione(regione);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/stato/{statoId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByStato(@PathVariable Long statoId) {
        Stato stato = new Stato();
        stato.setId(statoId);
        List<AutomobileDto> automobili = automobileService.findByStato(stato);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/alimentazione/{alimentazioneId}")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByAlimentazione(@PathVariable Long alimentazioneId) {
        Alimentazione alimentazione = new Alimentazione();
        alimentazione.setId(alimentazioneId);
        List<AutomobileDto> automobili = automobileService.findByAlimentazione(alimentazione);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/prezzo")
    public ResponseEntity<List<AutomobileDto>> getAutomobiliByPrezzoRange(
            @RequestParam BigDecimal prezzoMin,
            @RequestParam BigDecimal prezzoMax) {
        List<AutomobileDto> automobili = automobileService.findByPrezzoBetween(prezzoMin, prezzoMax);
        return ResponseEntity.ok(automobili);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AutomobileDto>> searchAutomobiliByFilters(
            @RequestParam(required = false) String marcaNome,
            @RequestParam(required = false) String modelloNome,
            @RequestParam(required = false) BigDecimal prezzoMin,
            @RequestParam(required = false) BigDecimal prezzoMax,
            @RequestParam(required = false) String statoNome,
            @RequestParam(required = false) String regioneNome,
            @RequestParam(required = false) String alimentazioneNome) {
        List<AutomobileDto> automobili = automobileService.searchByFilters(
                marcaNome, modelloNome, prezzoMin, prezzoMax, statoNome, regioneNome, alimentazioneNome);
        return ResponseEntity.ok(automobili);
    }

}
