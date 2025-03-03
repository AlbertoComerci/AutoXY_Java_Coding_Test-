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

import java.math.BigDecimal;
import java.util.List;
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
    
    
    public AutomobileDto read(Long id) {
        Automobile automobile = automobileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
        return convertToDto(automobile);
    }
    

    public AutomobileDto create(AutomobileRequestDto dto) {
        // Verifica che il DTO non sia nullo
        if (dto == null) {
            throw new IllegalArgumentException("Il Dto di richiesta è nullo");
        }
        
        if (dto.getUtenteId() == null || dto.getMarcaId() == null || dto.getModelloId() == null ||
        dto.getRegioneId() == null || dto.getStatoId() == null || dto.getAlimentazioneId() == null) {
            throw new IllegalArgumentException("Uno o più ID sono null. Controlla la richiesta.");
        }
        
        
        
        Utente utente = utenteRepository.findById(dto.getUtenteId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Utente non trovato"));
        
        Marca marca = marcaRepository.findById(dto.getMarcaId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Marca non trovata"));
        
        Modello modello = modelloRepository.findById(dto.getModelloId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Modello non trovato"));
        
        Regione regione = regioneRepository.findById(dto.getRegioneId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Regione non trovata"));
        
        Stato stato = statoRepository.findById(dto.getStatoId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Stato non trovato"));
        
        Alimentazione alimentazione = alimentazioneRepository.findById(dto.getAlimentazioneId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Alimentazione non trovata"));
        
        // Creazione della nuova automobile
        Automobile automobile = new Automobile();
        automobile.setAnno(dto.getAnno());
        automobile.setPrezzo(dto.getPrezzo());
        automobile.setKm(dto.getKm());
        automobile.setUtente(utente);
        automobile.setMarca(marca);
        automobile.setModello(modello);
        automobile.setRegione(regione);
        automobile.setStato(stato);
        automobile.setAlimentazione(alimentazione);
        
        // Salvataggio nel DB
        Automobile savedAutomobile = automobileRepository.save(automobile);
        
        // Conversione in DTO per la risposta
        return convertToDto(savedAutomobile);
    }
    
    
    
    public AutomobileDto update(Long id, AutomobileRequestDto dto) {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Automobile automobile = automobileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
        
        // if (existingAutomobile.getUtente().getId() != (userDetails.getId())) {
        //     throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Non sei autorizzato a modificare questa automobile");
        // }
        
        automobile.setAnno(dto.getAnno());
        automobile.setPrezzo(dto.getPrezzo());
        automobile.setKm(dto.getKm());
        
        
        
        Utente utente = utenteRepository.findById(dto.getUtenteId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Utente non trovato"));
        Marca marca = marcaRepository.findById(dto.getMarcaId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Marca non trovata"));
        Modello modello = modelloRepository.findById(dto.getModelloId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Modello non trovato"));
        Regione regione = regioneRepository.findById(dto.getRegioneId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Regione non trovata"));
        Stato stato = statoRepository.findById(dto.getStatoId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Stato non trovato"));
        Alimentazione alimentazione = alimentazioneRepository.findById(dto.getAlimentazioneId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Alimentazione non trovata"));
        
        automobile.setUtente(utente);
        automobile.setMarca(marca);
        automobile.setModello(modello);
        automobile.setRegione(regione);
        automobile.setStato(stato);
        automobile.setAlimentazione(alimentazione);
        
        Automobile updatedAutomobile = automobileRepository.save(automobile);
        
        
        return convertToDto(updatedAutomobile);
    }
    
    
    public void delete(Long id) {
        if (automobileRepository.existsById(id)) {
            automobileRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata");
        }
    }
    
    public List<AutomobileDto> findByUtente(Utente utente) {
        if (utente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }
        return automobileRepository.findByUtente(utente)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByMarca(Marca marca) {
        if (marca == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca non valida");
        }
        return automobileRepository.findByMarca(marca)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByModello(Modello modello) {
        if (modello == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modello non valido");
        }
        return automobileRepository.findByModello(modello)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByRegione(Regione regione) {
        if (regione == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regione non valida");
        }
        return automobileRepository.findByRegione(regione)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByStato(Stato stato) {
        if (stato == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stato non valido");
        }
        return automobileRepository.findByStato(stato)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    public List<AutomobileDto> findByAlimentazione(Alimentazione alimentazione) {
        if (alimentazione == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alimentazione non valida");
        }
        return automobileRepository.findByAlimentazione(alimentazione)
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
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
        
        if (marcaNome != null) {
            Marca marca = marcaRepository.findByNome(marcaNome);
            if (marca != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("marca"), marca));
            }
        }
        if (modelloNome != null) {
            Modello modello = modelloRepository.findByNome(modelloNome);
            if (modello != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("modello"), modello));
            }
        }
        if (prezzoMin != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("prezzo"), prezzoMin));
        }
        if (prezzoMax != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("prezzo"), prezzoMax));
        }
        if (statoNome != null) {
            Stato stato = statoRepository.findByNome(statoNome);
            if (stato != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("stato"), stato));
            }
        }
        if (regioneNome != null) {
            Regione regione = regioneRepository.findByNome(regioneNome);
            if (regione != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("regione"), regione));
            }
        }
        if (alimentazioneNome != null) {
            Alimentazione alimentazione = alimentazioneRepository.findByNome(alimentazioneNome);
            if (alimentazione != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("alimentazione"), alimentazione));
            }
        }
        
        return automobileRepository.findAll(spec).stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    
    // public boolean isOwner(Long automobileId, Long utenteId) {
    //     Automobile automobile = automobileRepository.findById(automobileId)
    //     .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
    //     return automobile.getUtente().getId() == (utenteId);
    // }
}







