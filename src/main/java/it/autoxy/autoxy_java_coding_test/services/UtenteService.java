package it.autoxy.autoxy_java_coding_test.services;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UtenteService {

    void saveUtente(UtenteDto utenteDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response);
    Utente findUtenteByEmail(String email);

}
