package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import it.autoxy.autoxy_java_coding_test.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        when(utenteRepository.findByEmail("test@test.it")).thenReturn(Optional.of(new Utente()));
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> utenteService.saveUtente(dto));
        assertEquals("Email già registrata", ex.getMessage());
    }

    @Test
    void saveUtente_savesIfEmailNotExists() {
        UtenteDto dto = new UtenteDto();
        dto.setEmail("test2@test.it");
        dto.setPassword("pw");
        when(utenteRepository.findByEmail("test2@test.it")).thenReturn(Optional.empty());
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

    @Test
    void findUtenteByEmail_returnsUtente() {
        Utente utente = new Utente();
        when(utenteRepository.findByEmail("mail@mail.it")).thenReturn(Optional.of(utente));
        assertEquals(utente, utenteService.findUtenteByEmail("mail@mail.it"));
    }

    @Test
    void find_returnsUtente() {
        Utente utente = new Utente();
        when(utenteRepository.findById(1L)).thenReturn(Optional.of(utente));
        assertEquals(utente, utenteService.find(1L));
    }

    @Test
    void find_throwsIfNotFound() {
        when(utenteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> utenteService.find(1L));
    }

    @Test
    void convertToDto_mapsFields() {
        Utente utente = new Utente();
        utente.setId(1L);
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setEmail("mario@rossi.it");
        UtenteDto dto = utenteService.convertToDto(utente);
        assertEquals(1L, dto.getId());
        assertEquals("Mario", dto.getNome());
        assertEquals("Rossi", dto.getCognome());
        assertEquals("mario@rossi.it", dto.getEmail());
    }

}
