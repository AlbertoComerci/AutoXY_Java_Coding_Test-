package it.autoxy.autoxy_java_coding_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Utente utente = utenteRepository.findByEmail(email);
    if (utente == null) {
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return new CustomUserDetails(utente);
}



}
