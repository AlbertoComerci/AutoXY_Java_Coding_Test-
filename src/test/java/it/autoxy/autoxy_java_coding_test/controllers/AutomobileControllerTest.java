package it.autoxy.autoxy_java_coding_test.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest: avvia l'intero contesto Spring, necessario per testare l'integrazione completa
@SpringBootTest
// @AutoConfigureMockMvc: configura MockMvc per eseguire chiamate HTTP simulate
@AutoConfigureMockMvc
// @Transactional: Ogni test viene eseguito in una transazione che viene annullata alla fine.
// Questo garantisce che ogni test parta dai dati puliti di data.sql, anche se un test precedente li modifica.
@Transactional
@Sql("/sql/test-data.sql") // Carica i dati di test dal file SQL prima di eseguire i test
class AutomobileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAutomobili() throws Exception {
        mockMvc.perform(get("/api/automobili"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAutomobileById() throws Exception {
        mockMvc.perform(get("/api/automobili/{id}", 101)) // ID dal nostro data.sql
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.marca").value("Fiat"));
    }

    @Test
    void testGetAutomobileById_NotFound() throws Exception {
        mockMvc.perform(get("/api/automobili/{id}", 999)) // ID non esistente
                .andExpect(status().isNotFound());
    }

    // Test corretto in modo da essere indipendente dallo stato del database
    @Test
    void testSearchByPriceRange() throws Exception {
        BigDecimal prezzoMin = new BigDecimal("10000.00");
        BigDecimal prezzoMax = new BigDecimal("20000.00");

        MvcResult result = mockMvc.perform(get("/api/automobili/prezzo")
                        .param("prezzoMin", prezzoMin.toString())
                        .param("prezzoMax", prezzoMax.toString()))
                .andExpect(status().isOk())
                .andReturn();

        // 1. Estrai la lista di DTO dalla risposta JSON
        String jsonResponse = result.getResponse().getContentAsString();
        List<AutomobileDto> automobiliNelRange = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        // 2. Assicurati che la lista non sia vuota (sappiamo dai dati di test che ci devono essere risultati)
        assertThat(automobiliNelRange).isNotEmpty();

        // 3. Verifica la CONDIZIONE per OGNI elemento nella lista
        for (AutomobileDto auto : automobiliNelRange) {
            assertThat(auto.getPrezzo()).isGreaterThanOrEqualTo(prezzoMin);
            assertThat(auto.getPrezzo()).isLessThanOrEqualTo(prezzoMax);
        }
    }

    // @WithMockUser simula un utente autenticato.
    @Test
    @WithMockUser
    void testCreateAutomobile() throws Exception {
        // CORREZIONE: Usiamo il DTO corretto per la richiesta
        AutomobileRequestDto requestDto = new AutomobileRequestDto();
        requestDto.setAnno(2023);
        requestDto.setPrezzo(new BigDecimal("22000.00")); // crea un BigDecimal
        requestDto.setKm(100);
        requestDto.setUtenteId(1L);
        requestDto.setMarcaId(1L);
        requestDto.setModelloId(1L);
        requestDto.setRegioneId(1L);
        requestDto.setStatoId(1L);
        requestDto.setAlimentazioneId(1L);

        mockMvc.perform(post("/api/automobili")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated()) // POST di successo restituisce 201 Created
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.prezzo").value(22000.00));
    }

    @Test
    @WithMockUser
    void testUpdateAutomobile() throws Exception {
        // CORREZIONE: Usiamo il DTO corretto per la richiesta
        AutomobileRequestDto requestDto = new AutomobileRequestDto();
        requestDto.setAnno(2021);
        requestDto.setPrezzo(new BigDecimal("16500.00")); //crea un BigDecimal
        requestDto.setKm(55000);
        requestDto.setUtenteId(1L);
        requestDto.setMarcaId(1L);
        requestDto.setModelloId(1L);
        requestDto.setRegioneId(1L);
        requestDto.setStatoId(1L);
        requestDto.setAlimentazioneId(1L);

        mockMvc.perform(put("/api/automobili/{id}", 101) // Aggiorniamo l'auto con ID 101
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.prezzo").value(16500.00));
    }

    @Test
    @WithMockUser
    void testDeleteAutomobile() throws Exception {
        mockMvc.perform(delete("/api/automobili/{id}", 101))
                .andExpect(status().isNoContent()); // DELETE di successo restituisce 204 No Content

        // Verifichiamo che l'auto sia stata davvero cancellata
        mockMvc.perform(get("/api/automobili/{id}", 101))
                .andExpect(status().isNotFound());
    }
}