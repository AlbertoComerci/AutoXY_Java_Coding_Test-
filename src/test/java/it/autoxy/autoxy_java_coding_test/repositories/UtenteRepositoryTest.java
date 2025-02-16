package it.autoxy.autoxy_java_coding_test.repositories;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;
import it.autoxy.autoxy_java_coding_test.repositories.AlimentazioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.AutomobileRepository;
import it.autoxy.autoxy_java_coding_test.repositories.MarcaRepository;
import it.autoxy.autoxy_java_coding_test.repositories.ModelloRepository;
import it.autoxy.autoxy_java_coding_test.repositories.RegioneRepository;
import it.autoxy.autoxy_java_coding_test.repositories.StatoRepository;
import it.autoxy.autoxy_java_coding_test.repositories.UtenteRepository;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UtenteRepositoryTest {

    @Autowired
    private AutomobileRepository automobileRepository;
    
    @Autowired
    private MarcaRepository marcaRepository;
    
    @Autowired
    private ModelloRepository modelloRepository;
    
    @Autowired
    private RegioneRepository regioneRepository;
    
    @Autowired
    private StatoRepository statoRepository;
    
    @Autowired
    private AlimentazioneRepository alimentazioneRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    private Utente utenteTest1;
    private Automobile autoTest1;
    private Automobile autoTest2;
    
    @BeforeEach
    void setUp(){
        utenteTest1 = new Utente();
        utenteTest1.setUsername("Piero");
        utenteTest1.setEmail("piero@test.it");
        utenteTest1.setPassword("123password");
        utenteRepository.save(utenteTest1);
        
        // Dati esistenti nel database
        Marca marca = marcaRepository.findByNome("Fiat");
        Modello modello = modelloRepository.findByMarca(marca).get(0); // Primo modello della Fiat
        Regione regione = regioneRepository.findByNome("Lombardia");
        Stato stato = statoRepository.findByNome("Disponibile");
        Alimentazione alimentazione = alimentazioneRepository.findByNome("Benzina");
        
        autoTest1 = new Automobile();
        autoTest1.setAnno(2020);
        autoTest1.setPrezzo(new BigDecimal("15000.00"));
        autoTest1.setKm(50000);
        autoTest1.setUtente(utenteTest1);
        autoTest1.setMarca(marca);
        autoTest1.setModello(modello);
        autoTest1.setRegione(regione);
        autoTest1.setStato(stato);
        autoTest1.setAlimentazione(alimentazione);
        automobileRepository.save(autoTest1);
        
        autoTest2 = new Automobile();
        autoTest2.setAnno(2024);
        autoTest2.setPrezzo(new BigDecimal("25000.00"));
        autoTest2.setKm(10000);
        autoTest2.setUtente(utenteTest1);
        autoTest2.setMarca(marca);
        autoTest2.setModello(modello);
        autoTest2.setRegione(regione);
        autoTest2.setStato(stato);
        autoTest2.setAlimentazione(alimentazione);
        automobileRepository.save(autoTest2);
    }
    
    
    
    
    @Test
    void testFindByEmail() {
        Utente utente = utenteRepository.findByEmail("piero@test.it");
        
        assertThat(utente)
        .isNotNull()
        .extracting(Utente::getUsername, Utente::getPassword)
        .containsExactly("Piero", "123password");
    }
    
    
    
    
    @Test
    void testDeleteUtente(){
        Iterable<Utente> utenti = utenteRepository.findAll();
        Utente utente = utenti.iterator().next();

        List<Automobile> automobili = automobileRepository.findByUtente(utente);
        automobileRepository.deleteAll(automobili);

        utenteRepository.delete(utente);
        assertThat(utenteRepository.findAll()).hasSize(1);
    }
    
    @Test
    void testUpdateUtente(){
        Iterable<Utente> utenti = utenteRepository.findAll();
        Utente utente = utenti.iterator().next();
        utente.setUsername("Franco");
        utenteRepository.save(utente);
        
        assertThat(utenteRepository.findById(utente.getId())).get()
        .extracting("username")
        .isEqualTo("Franco");
    }

}
