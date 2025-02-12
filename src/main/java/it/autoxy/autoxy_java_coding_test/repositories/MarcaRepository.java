package it.autoxy.autoxy_java_coding_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Marca findByNome(String nome);
}

