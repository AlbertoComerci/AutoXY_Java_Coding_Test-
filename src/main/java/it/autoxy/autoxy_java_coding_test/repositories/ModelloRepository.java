package it.autoxy.autoxy_java_coding_test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;

@Repository
public interface ModelloRepository extends JpaRepository<Modello, Long> {
    List<Modello> findByMarca(Marca marca);
}

