package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtenteServiceImplUnitTest {
    @Mock
    private UtenteRepository utenteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UtenteServiceImpl utenteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUtente_throwsIfEmailExists() {
        UtenteDto dto = new UtenteDto();
        dto.setEmail("test@test.it");
        when(utenteRepository.findByEmail("test@test.it")).thenReturn(new Utente());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> utenteService.saveUtente(dto));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void saveUtente_savesIfEmailNotExists() {
        UtenteDto dto = new UtenteDto();
        dto.setEmail("test2@test.it");
        dto.setPassword("pw");
        when(utenteRepository.findByEmail("test2@test.it")).thenReturn(null);
        when(passwordEncoder.encode("pw")).thenReturn("hashed");
        Utente utente = new Utente();
        when(utenteRepository.save(any(Utente.class))).thenReturn(utente);
        assertNotNull(utenteService.saveUtente(dto));
    }

    @Test
    void getAll_returnsList() {
        Utente utente = new Utente();
        when(utenteRepository.findAll()).thenReturn(Collections.singletonList(utente));
        List<UtenteDto> result = utenteService.getAll();
        assertEquals(1, result.size());
    }

    // Altri test per find, convertToDto ecc.
}
