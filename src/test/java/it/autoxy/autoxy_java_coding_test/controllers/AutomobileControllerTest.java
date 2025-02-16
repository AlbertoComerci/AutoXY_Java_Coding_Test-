package it.autoxy.autoxy_java_coding_test.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.models.*;
import it.autoxy.autoxy_java_coding_test.repositories.AlimentazioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.AutomobileRepository;
import it.autoxy.autoxy_java_coding_test.repositories.MarcaRepository;
import it.autoxy.autoxy_java_coding_test.repositories.ModelloRepository;
import it.autoxy.autoxy_java_coding_test.repositories.RegioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.StatoRepository;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import it.autoxy.autoxy_java_coding_test.services.AutomobileService;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AutomobileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutomobileService automobileService;

    @Autowired
    private AutomobileRepository automobileRepository;

    @Autowired
    private MarcaRepository marcaRepository;
    
    @Autowired
    private ModelloRepository modelloRepository;
    
    @Autowired
    private RegioneRepository regioneRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @Autowired
    private AlimentazioneRepository alimentazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    private Utente utenteTest1;
    private Automobile autoTest1;
    private Automobile autoTest2;

    @BeforeEach
    void setUp(){
        utenteTest1 = new Utente();
        utenteTest1.setUsername("Piero");
        utenteTest1.setEmail("piero@test.it");
        utenteTest1.setPassword("123password");
        utenteRepository.save(utenteTest1);
        
        // Dati esistenti nel database
        Marca marca = marcaRepository.findByNome("Fiat");
        Modello modello = modelloRepository.findByMarca(marca).get(0); // Primo modello della Fiat
        Regione regione = regioneRepository.findByNome("Lombardia");
        Stato stato = statoRepository.findByNome("Disponibile");
        Alimentazione alimentazione = alimentazioneRepository.findByNome("Benzina");
        
        autoTest1 = new Automobile();
        autoTest1.setAnno(2020);
        autoTest1.setPrezzo(new BigDecimal("15000.00"));
        autoTest1.setKm(50000);
        autoTest1.setUtente(utenteTest1);
        autoTest1.setMarca(marca);
        autoTest1.setModello(modello);
        autoTest1.setRegione(regione);
        autoTest1.setStato(stato);
        autoTest1.setAlimentazione(alimentazione);
        automobileRepository.save(autoTest1);
        
        autoTest2 = new Automobile();
        autoTest2.setAnno(2024);
        autoTest2.setPrezzo(new BigDecimal("25000.00"));
        autoTest2.setKm(10000);
        autoTest2.setUtente(utenteTest1);
        autoTest2.setMarca(marca);
        autoTest2.setModello(modello);
        autoTest2.setRegione(regione);
        autoTest2.setStato(stato);
        autoTest2.setAlimentazione(alimentazione);
        automobileRepository.save(autoTest2);
    }

    @Test
    void testGetAllAutomobili() throws Exception {
        mockMvc.perform(get("/api/automobili"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // Verifica che sia restituito un array
    }

    @Test
    void testGetAutomobileById() throws Exception {
        mockMvc.perform(get("/api/automobili/" + autoTest1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.anno").value(2020))
                .andExpect(jsonPath("$.prezzo").value(15000));
    }

    @Test
    void testCreateAutomobile() throws Exception {

        Automobile nuovaAuto = new Automobile();
        nuovaAuto.setUtente(utenteTest1);
        nuovaAuto.setAnno(2023);
        nuovaAuto.setPrezzo(new BigDecimal("25000"));
        nuovaAuto.setKm(5000);
        nuovaAuto.setRegione(regioneRepository.findByNome("Sicilia"));
        nuovaAuto.setStato(statoRepository.findByNome("Disponibile"));
        nuovaAuto.setAlimentazione(alimentazioneRepository.findByNome("Benzina"));

        mockMvc.perform(post("/api/automobili")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuovaAuto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.anno").value(2023))
                .andExpect(jsonPath("$.prezzo").value(25000));
    }

    @Test
    void testUpdateAutomobile() throws Exception {
        autoTest1.setPrezzo(new BigDecimal("18000"));

        mockMvc.perform(put("/api/automobili/" + autoTest1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autoTest1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prezzo").value(18000));
    }

    @Test
    void testDeleteAutomobile() throws Exception {
        mockMvc.perform(delete("/api/automobili/" + autoTest1.getId()))
                .andExpect(status().isNoContent());

        // Controllo che l'auto sia stata effettivamente eliminata
        mockMvc.perform(get("/api/automobili/" + autoTest1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAutomobiliByPrezzoRange() throws Exception {
        mockMvc.perform(get("/api/automobili/prezzo")
                .param("prezzoMin", "15000")
                .param("prezzoMax", "25000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}

