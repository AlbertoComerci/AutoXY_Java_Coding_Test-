package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
import it.autoxy.autoxy_java_coding_test.dtos.AutomobileRequestDto;
import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.AlimentazioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.AutomobileRepository;
import it.autoxy.autoxy_java_coding_test.repositories.MarcaRepository;
import it.autoxy.autoxy_java_coding_test.repositories.ModelloRepository;
import it.autoxy.autoxy_java_coding_test.repositories.RegioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.StatoRepository;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.autoxy.autoxy_java_coding_test.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutomobileService  {
    
    @Autowired
    private AutomobileRepository automobileRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
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
    private ModelMapper modelMapper;
    
    // Metodo per convertire Automobile in AutomobileDto
    private AutomobileDto convertToDto(Automobile automobile) {
        return modelMapper.map(automobile, AutomobileDto.class);
    }
    
    public List<AutomobileDto> readAll() {
        return automobileRepository.findAll().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    // Utility DRY generica per validare entità da repository
    private <T> T getOrThrow(java.util.Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(message));
    }

    // Utility DRY generica per validare argomenti non nulli
    private <T> T requireNonNull(T obj, String message) {
        if (obj == null) throw new ResourceNotFoundException(message);
        return obj;
    }
    
    public AutomobileDto read(Long id) {
        Automobile automobile = getOrThrow(automobileRepository.findById(id), "Automobile con id " + id + " non trovata.");
        return convertToDto(automobile);
    }
    
    public AutomobileDto createAutomobile(AutomobileRequestDto automobileRequestDto) {
    Automobile automobile = new Automobile();
    // Questo metodo imposta tutti i campi, recuperando le entità tramite ID
    mapDtoToAutomobile(automobile, automobileRequestDto); 
    
    // Ora possiamo salvare direttamente
    Automobile saved = automobileRepository.save(automobile);
    
    return convertToDto(saved);
}
    
    public AutomobileDto updateAutomobile(Long id, AutomobileRequestDto automobileRequestDto) {
        Automobile existingAutomobile = getOrThrow(automobileRepository.findById(id), "Automobile con id " + id + " non trovata.");
        mapDtoToAutomobile(existingAutomobile, automobileRequestDto);
        Automobile saved = automobileRepository.save(existingAutomobile);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        if (automobileRepository.existsById(id)) {
            automobileRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Automobile non trovata");
        }
    }
    
    public List<AutomobileDto> findByUtente(Utente utente) {
        requireNonNull(utente, "Utente non trovato");
        return automobileRepository.findByUtente(utente)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByMarca(Marca marca) {
        requireNonNull(marca, "Marca non valida");
        return automobileRepository.findByMarca(marca)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByModello(Modello modello) {
        requireNonNull(modello, "Modello non valido");
        return automobileRepository.findByModello(modello)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByRegione(Regione regione) {
        requireNonNull(regione, "Regione non valida");
        return automobileRepository.findByRegione(regione)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByStato(Stato stato) {
        requireNonNull(stato, "Stato non valido");
        return automobileRepository.findByStato(stato)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByAlimentazione(Alimentazione alimentazione) {
        requireNonNull(alimentazione, "Alimentazione non valida");
        return automobileRepository.findByAlimentazione(alimentazione)
            .stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByPrezzoBetween(BigDecimal prezzoMin, BigDecimal prezzoMax) {
        // Controlla se i prezzi sono null
        if (prezzoMin == null || prezzoMax == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prezzo minimo o massimo non valido");
        }
        
        // Controlla se i prezzi sono positivi
        if (prezzoMin.compareTo(BigDecimal.ZERO) < 0 || prezzoMax.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "I prezzi devono essere numeri positivi");
        }
        
        // Controlla se prezzoMin è maggiore di prezzoMax
        if (prezzoMin.compareTo(prezzoMax) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il prezzo minimo non può essere maggiore del prezzo massimo");
        }
        
        return automobileRepository.findByPrezzoBetween(prezzoMin, prezzoMax)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    
    
    // Query dinamiche per la ricerca nel database
    public List<AutomobileDto> searchByFilters(String marcaNome, String modelloNome, BigDecimal prezzoMin, BigDecimal prezzoMax,
        String statoNome, String regioneNome, String alimentazioneNome) {
    
    Specification<Automobile> spec = Specification.where(null);
    
    if (marcaNome != null && !marcaNome.isEmpty()) {
        // 1. Ottieni l'Optional dal repository
        Optional<Marca> marcaOpt = marcaRepository.findByNome(marcaNome);
        // 2. Se l'Optional contiene un valore, usalo per aggiungere il filtro
        if (marcaOpt.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("marca"), marcaOpt.get()));
        } else {
            // Se la marca richiesta non esiste, la ricerca non deve produrre risultati
            return Collections.emptyList();
        }
    }
    if (modelloNome != null && !modelloNome.isEmpty()) {
        Optional<Modello> modelloOpt = modelloRepository.findByNome(modelloNome);
        if (modelloOpt.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("modello"), modelloOpt.get()));
        } else {
            return Collections.emptyList();
        }
    }
    if (prezzoMin != null) {
        spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("prezzo"), prezzoMin));
    }
    if (prezzoMax != null) {
        spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("prezzo"), prezzoMax));
    }
    if (statoNome != null && !statoNome.isEmpty()) {
        Optional<Stato> statoOpt = statoRepository.findByNome(statoNome);
        if (statoOpt.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stato"), statoOpt.get()));
        } else {
            return Collections.emptyList();
        }
    }
    if (regioneNome != null && !regioneNome.isEmpty()) {
        Optional<Regione> regioneOpt = regioneRepository.findByNome(regioneNome);
        if (regioneOpt.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("regione"), regioneOpt.get()));
        } else {
            return Collections.emptyList();
        }
    }
    if (alimentazioneNome != null && !alimentazioneNome.isEmpty()) {
        Optional<Alimentazione> alimentazioneOpt = alimentazioneRepository.findByNome(alimentazioneNome);
        if (alimentazioneOpt.isPresent()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("alimentazione"), alimentazioneOpt.get()));
        } else {
            return Collections.emptyList();
        }
    }
    
    return automobileRepository.findAll(spec).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}
    

    private void mapDtoToAutomobile(Automobile automobile, AutomobileRequestDto dto) {
        automobile.setAnno(dto.getAnno());
        automobile.setPrezzo(dto.getPrezzo());
        automobile.setKm(dto.getKm());
        automobile.setUtente(getOrThrow(utenteRepository.findById(dto.getUtenteId()), "Utente con id " + dto.getUtenteId() + " non trovato."));
        automobile.setMarca(getOrThrow(marcaRepository.findById(dto.getMarcaId()), "Marca con id " + dto.getMarcaId() + " non trovata."));
        automobile.setModello(getOrThrow(modelloRepository.findById(dto.getModelloId()), "Modello con id " + dto.getModelloId() + " non trovato."));
        automobile.setRegione(getOrThrow(regioneRepository.findById(dto.getRegioneId()), "Regione con id " + dto.getRegioneId() + " non trovata."));
        automobile.setStato(getOrThrow(statoRepository.findById(dto.getStatoId()), "Stato con id " + dto.getStatoId() + " non trovato."));
        automobile.setAlimentazione(getOrThrow(alimentazioneRepository.findById(dto.getAlimentazioneId()), "Alimentazione con id " + dto.getAlimentazioneId() + " non trovata."));
    }
    
}







