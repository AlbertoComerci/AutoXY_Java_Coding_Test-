package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileRequestDto;
import it.autoxy.autoxy_java_coding_test.exceptions.ResourceNotFoundException;
import it.autoxy.autoxy_java_coding_test.models.*;
import it.autoxy.autoxy_java_coding_test.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutomobileServiceUnitTest {
    @Mock
    private AutomobileRepository automobileRepository;
    @Mock
    private UtenteRepository utenteRepository;
    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private ModelloRepository modelloRepository;
    @Mock
    private RegioneRepository regioneRepository;
    @Mock
    private StatoRepository statoRepository;
    @Mock
    private AlimentazioneRepository alimentazioneRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AutomobileService automobileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void read_throwsExceptionIfNotFound() {
        when(automobileRepository.findById(1L)).thenReturn(Optional.empty());
        // Verifica che venga lanciata la nostra eccezione custom
        assertThrows(ResourceNotFoundException.class, () -> automobileService.read(1L));
    }

    @Test
    void read_returnsAutomobileDto() {
        Automobile auto = new Automobile();
        auto.setId(1L);
        when(automobileRepository.findById(1L)).thenReturn(Optional.of(auto));
        AutomobileDto dto = new AutomobileDto();
        when(modelMapper.map(auto, AutomobileDto.class)).thenReturn(dto);
        assertEquals(dto, automobileService.read(1L));
    }

    @Test
    void getAll_returnsList() {
        Automobile auto = new Automobile();
        when(automobileRepository.findAll()).thenReturn(Collections.singletonList(auto));
        AutomobileDto dto = new AutomobileDto();
        when(modelMapper.map(auto, AutomobileDto.class)).thenReturn(dto);
        List<AutomobileDto> result = automobileService.readAll();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void create_throwsIfUtenteNotFound() {
        AutomobileRequestDto req = new AutomobileRequestDto();
        req.setUtenteId(1L);
        when(utenteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> automobileService.createAutomobile(req));
    }

    @Test
    void update_throwsIfNotFound() {
        AutomobileRequestDto req = new AutomobileRequestDto();
        when(automobileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> automobileService.updateAutomobile(1L, req));
    }

    @Test
    void delete_throwsIfNotFound() {
        when(automobileRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> automobileService.delete(1L));
    }

    @Test
    void delete_deletesIfExists() {
        when(automobileRepository.existsById(1L)).thenReturn(true);
        doNothing().when(automobileRepository).deleteById(1L);
        assertDoesNotThrow(() -> automobileService.delete(1L));
        verify(automobileRepository).deleteById(1L);
    }

    @Test
    void findByUtente_throwsIfNull() {
        assertThrows(ResourceNotFoundException.class, () -> automobileService.findByUtente(null));
    }

    @Test
    void findByUtente_returnsList() {
        Utente utente = new Utente();
        Automobile auto = new Automobile();
        when(automobileRepository.findByUtente(utente)).thenReturn(Collections.singletonList(auto));
        AutomobileDto dto = new AutomobileDto();
        when(modelMapper.map(auto, AutomobileDto.class)).thenReturn(dto);
        List<AutomobileDto> result = automobileService.findByUtente(utente);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void findByMarca_throwsIfNull() {
        assertThrows(ResourceNotFoundException.class, () -> automobileService.findByMarca(null));
    }

    @Test
    void findByMarca_returnsList() {
        Marca marca = new Marca();
        Automobile auto = new Automobile();
        when(automobileRepository.findByMarca(marca)).thenReturn(Collections.singletonList(auto));
        AutomobileDto dto = new AutomobileDto();
        when(modelMapper.map(auto, AutomobileDto.class)).thenReturn(dto);
        List<AutomobileDto> result = automobileService.findByMarca(marca);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void findByPrezzoBetween_throwsIfNull() {
        assertThrows(Exception.class, () -> automobileService.findByPrezzoBetween(null, BigDecimal.TEN));
        assertThrows(Exception.class, () -> automobileService.findByPrezzoBetween(BigDecimal.TEN, null));
    }

    @Test
    void findByPrezzoBetween_returnsList() {
        Automobile auto = new Automobile();
        when(automobileRepository.findByPrezzoBetween(BigDecimal.ONE, BigDecimal.TEN)).thenReturn(Collections.singletonList(auto));
        AutomobileDto dto = new AutomobileDto();
        when(modelMapper.map(auto, AutomobileDto.class)).thenReturn(dto);
        List<AutomobileDto> result = automobileService.findByPrezzoBetween(BigDecimal.ONE, BigDecimal.TEN);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
