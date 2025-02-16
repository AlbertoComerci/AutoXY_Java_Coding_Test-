package it.autoxy.autoxy_java_coding_test.services;

import it.autoxy.autoxy_java_coding_test.dtos.UtenteDto;
import it.autoxy.autoxy_java_coding_test.models.Utente;


public interface UtenteService {

    Utente saveUtente(UtenteDto utenteDto);

    Utente findUtenteByEmail(String email);

    Utente find(long id);

    

}
