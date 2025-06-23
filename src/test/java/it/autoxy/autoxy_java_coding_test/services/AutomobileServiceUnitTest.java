package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileRequestDto;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Collections;
import java.util.List;
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
        assertThrows(ResponseStatusException.class, () -> automobileService.read(1L));
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
        assertThrows(ResponseStatusException.class, () -> automobileService.create(req));
    }

    // Altri test per update, delete, filtri ecc.
}
