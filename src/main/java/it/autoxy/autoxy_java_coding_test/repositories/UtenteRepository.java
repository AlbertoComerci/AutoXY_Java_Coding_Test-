package it.autoxy.autoxy_java_coding_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}

