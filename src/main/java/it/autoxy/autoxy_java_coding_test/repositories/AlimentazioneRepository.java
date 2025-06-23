package it.autoxy.autoxy_java_coding_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;

@Repository
public interface AlimentazioneRepository extends JpaRepository<Alimentazione, Long> {
    Optional<Alimentazione> findByNome(String nome);
}

