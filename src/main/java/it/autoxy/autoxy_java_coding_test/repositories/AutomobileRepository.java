package it.autoxy.autoxy_java_coding_test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile, Long> {
    List<Automobile> findByUtente(Utente utente);
    List<Automobile> findByMarca(Marca marca);
    List<Automobile> findByRegione(Regione regione);
    List<Automobile> findByStato(Stato stato);
    List<Automobile> findByAlimentazione(Alimentazione alimentazione);
    List<Automobile> findByPrezzoBetween(double prezzoMin, double prezzoMax);
}

