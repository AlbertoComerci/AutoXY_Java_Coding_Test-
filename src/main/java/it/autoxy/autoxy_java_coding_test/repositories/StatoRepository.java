package it.autoxy.autoxy_java_coding_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import it.autoxy.autoxy_java_coding_test.models.Stato;

@Repository
public interface StatoRepository extends JpaRepository<Stato, Long> {
    Optional<Stato> findByNome(String nome);
}

