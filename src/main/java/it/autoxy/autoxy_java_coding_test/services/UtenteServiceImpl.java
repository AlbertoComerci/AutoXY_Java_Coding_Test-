package it.autoxy.autoxy_java_coding_test.services;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.exceptions.ResourceNotFoundException;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<UtenteDto> getAll() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
    }
    
    
    @Override
    public Utente findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }
    
    @Override
    public Utente saveUtente(UtenteDto utenteDto) {
        // Verifica se l'email è già registrata
        if (utenteRepository.findByEmail(utenteDto.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("Email già registrata");
        }
        
        // Crea un nuovo utente
        Utente utente = new Utente();
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        
        // Salva l'utente nel database
        return utenteRepository.save(utente);
    }
    
    @Override
    public Utente find(long id) {
        return utenteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }
    
    public UtenteDto convertToDto(Utente utente) {
        
        UtenteDto dto = new UtenteDto();
        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setCognome(utente.getCognome());
        dto.setEmail(utente.getEmail());
        
        return dto;
    }
    
}
