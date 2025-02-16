package it.autoxy.autoxy_java_coding_test.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Utente findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public Utente saveUtente(UtenteDto utenteDto) {
        // Verifica se l'email è già registrata
        if (utenteRepository.findByEmail(utenteDto.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email già registrata");
        }

        // Crea un nuovo utente
        Utente utente = new Utente();
        utente.setUsername(utenteDto.getNome() + " " + utenteDto.getCognome());
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));

        // Salva l'utente nel database
        return utenteRepository.save(utente);
    }

    @Override
    public Utente find(long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }

}
