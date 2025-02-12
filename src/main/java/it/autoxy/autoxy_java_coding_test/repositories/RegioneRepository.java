package it.autoxy.autoxy_java_coding_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Regione;

@Repository
public interface RegioneRepository extends JpaRepository<Regione, Long> {
    Regione findByNome(String nome);
}

