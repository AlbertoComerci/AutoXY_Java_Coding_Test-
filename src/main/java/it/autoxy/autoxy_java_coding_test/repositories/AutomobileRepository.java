package it.autoxy.autoxy_java_coding_test.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile, Long>, JpaSpecificationExecutor<Automobile> {
    @Override
    @EntityGraph(attributePaths = {"utente", "marca", "modello", "regione", "stato", "alimentazione"})
    List<Automobile> findAll();

    @Override
    @EntityGraph(attributePaths = {"utente", "marca", "modello", "regione", "stato", "alimentazione"})
    Optional<Automobile> findById(Long id);
    
    List<Automobile> findByUtente(Utente utente);
    List<Automobile> findByMarca(Marca marca);
    List<Automobile> findByModello(Modello modello);
    List<Automobile> findByRegione(Regione regione);
    List<Automobile> findByStato(Stato stato);
    List<Automobile> findByAlimentazione(Alimentazione alimentazione);
    List<Automobile> findByPrezzoBetween(BigDecimal prezzoMin, BigDecimal prezzoMax);
}

