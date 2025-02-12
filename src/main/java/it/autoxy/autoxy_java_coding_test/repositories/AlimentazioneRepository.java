package it.autoxy.autoxy_java_coding_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;

@Repository
public interface AlimentazioneRepository extends JpaRepository<Alimentazione, Long> {
    Alimentazione findByNome(String nome);
}

