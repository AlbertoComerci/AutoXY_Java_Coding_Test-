package it.autoxy.autoxy_java_coding_test.services;

import java.util.List;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;


public interface UtenteService {

    List<UtenteDto> getAll();

    Utente saveUtente(UtenteDto utenteDto);

    Utente findUtenteByEmail(String email);

    Utente find(long id);

    

}
