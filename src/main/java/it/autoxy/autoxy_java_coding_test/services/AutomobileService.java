package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.AutomobileDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutomobileService implements CrudService<AutomobileDto, Automobile, Long> {
    
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
    
    @Override
    public List<AutomobileDto> readAll() {
        return automobileRepository.findAll().stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    @Override
    public AutomobileDto read(Long id) {
        Automobile automobile = automobileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
        return convertToDto(automobile);
    }
    
    @Override
    public AutomobileDto create(Automobile automobile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Utente utente = utenteRepository.findById(userDetails.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
            automobile.setUtente(utente);
        }
        
        regioneRepository.findById(automobile.getRegione().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regione non valida"));
        
        statoRepository.findById(automobile.getStato().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stato non valido"));
        
        alimentazioneRepository.findById(automobile.getAlimentazione().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alimentazione non valida"));
        
        Marca marca = marcaRepository.findById(automobile.getMarca().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca non valida"));
        
        // Verifica se il modello appartiene alla marca
        Modello modello = modelloRepository.findById(automobile.getModello().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modello non valido"));
        
        if (!modello.getMarca().equals(marca)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il modello selezionato non appartiene alla marca scelta");
        }
        return convertToDto(automobileRepository.save(automobile));
    }
    
    @Override
    public AutomobileDto update(Long id, Automobile updatedAutomobile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Automobile existingAutomobile = automobileRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
        
        if (existingAutomobile.getUtente().getId() != (userDetails.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Non sei autorizzato a modificare questa automobile");
        }
        
        regioneRepository.findById(updatedAutomobile.getRegione().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regione non valida"));
        
        statoRepository.findById(updatedAutomobile.getStato().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stato non valido"));
        
        alimentazioneRepository.findById(updatedAutomobile.getAlimentazione().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alimentazione non valida"));
        
        
        Marca marca = marcaRepository.findById(updatedAutomobile.getMarca().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca non valida"));
        
        // Verifica se il modello appartiene alla marca
        Modello modello = modelloRepository.findById(updatedAutomobile.getModello().getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modello non valido"));
        
        if (!modello.getMarca().equals(marca)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il modello selezionato non appartiene alla marca scelta");
        }
        
        existingAutomobile.setAnno(updatedAutomobile.getAnno());
        existingAutomobile.setPrezzo(updatedAutomobile.getPrezzo());
        existingAutomobile.setKm(updatedAutomobile.getKm());
        existingAutomobile.setMarca(updatedAutomobile.getMarca());
        existingAutomobile.setModello(updatedAutomobile.getModello());
        existingAutomobile.setRegione(updatedAutomobile.getRegione());
        existingAutomobile.setStato(updatedAutomobile.getStato());
        existingAutomobile.setAlimentazione(updatedAutomobile.getAlimentazione());
        
        return convertToDto(automobileRepository.save(existingAutomobile));
    }
    
    @Override
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
    
    
    public boolean isOwner(Long automobileId, Long utenteId) {
        Automobile automobile = automobileRepository.findById(automobileId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Automobile non trovata"));
        return automobile.getUtente().getId() == (utenteId);
    }
}







