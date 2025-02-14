package it.autoxy.autoxy_java_coding_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public Utente findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public void saveUtente(UtenteDto utenteDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
        Utente utente = new Utente();
        utente.setUsername(utenteDto.getNome() + " " + utenteDto.getCognome());
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder().encode(utenteDto.getPassword()));

        utenteRepository.save(utente);
    }

}
