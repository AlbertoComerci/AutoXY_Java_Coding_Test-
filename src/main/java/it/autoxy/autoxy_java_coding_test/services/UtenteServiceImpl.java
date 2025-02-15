package it.autoxy.autoxy_java_coding_test.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Utente findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public void saveUtente(UtenteDto utenteDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
        Utente utente = new Utente();
        utente.setUsername(utenteDto.getNome() + " " + utenteDto.getCognome());
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));

        utenteRepository.save(utente);
    }

    // effettua una login tramite token all’interno della sessione mantenendola attiva
    // public void authenticateUserAndSetSession(Utente utente, UtenteDto utenteDto, HttpServletRequest request) {
    //     try {
    //         CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(utente.getEmail());
    //         UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), utenteDto.getPassword());
            
    //         Authentication authentication = authenticationManager.authenticate(authToken);
            
    //         SecurityContextHolder.getContext().setAuthentication(authentication);
            
    //         HttpSession session = request.getSession(true);
    //         session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    //     } catch (AuthenticationException e) {
    //         e.printStackTrace();
    //     }
        
    // }

    @Override
    public Utente find(long id) {
        return utenteRepository.findById(id).get();
    }

}
