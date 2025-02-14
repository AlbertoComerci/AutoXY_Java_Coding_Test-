package it.autoxy.autoxy_java_coding_test;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AutomobileRepositoryTest {
    
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
    void testFindByUtente() {
        Utente utente = utenteRepository.findByEmail("piero@test.it");
        List<Automobile> result = automobileRepository.findByUtente(utente);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    public void whenFindByUtente_ShouldReturnAutomobili() {
        
        List<Automobile> found = automobileRepository.findByUtente(utenteTest1);
        
        
        assertThat(found).hasSize(2)
        .contains(autoTest1, autoTest2)
        .allMatch(auto -> auto.getUtente().getEmail().equals("piero@test.it"));
    }
    
    @Test
    void testFindByMarca() {
        Marca marca = marcaRepository.findByNome("Fiat");
        List<Automobile> result = automobileRepository.findByMarca(marca);
        assertThat(result).isNotEmpty();
    }
    
    void whenFindByMarca_ShouldReturnAutomobili() {
        
        Marca fiat = marcaRepository.findByNome("Fiat");
        
        
        List<Automobile> found = automobileRepository.findByMarca(fiat);
        
        
        assertThat(found).isNotEmpty()
        .allMatch(auto -> auto.getMarca().getNome().equals("Fiat"));
    }
    
    @Test
    void testFindByModello() {
        Modello modello = modelloRepository.findByNome("Panda");
        List<Automobile> result = automobileRepository.findByModello(modello);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    void whenFindByModello_ShouldReturnAutomobili() {
        
        Marca fiat = marcaRepository.findByNome("Fiat");
        Modello panda = modelloRepository.findByMarca(fiat)
        .stream()
        .filter(m -> m.getNome().equals("Panda"))
        .findFirst()
        .orElseThrow();
        
        
        List<Automobile> found = automobileRepository.findByModello(panda);
        
        
        assertThat(found).allMatch(auto -> auto.getModello().getNome().equals("Panda"));
    }
    
    @Test
    void testFindByRegione() {
        Regione regione = regioneRepository.findByNome("Lombardia");
        List<Automobile> result = automobileRepository.findByRegione(regione);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    void whenFindByRegione_ShouldReturnAutomobili() {
        
        Regione lombardia = regioneRepository.findByNome("Lombardia");
        
        
        List<Automobile> found = automobileRepository.findByRegione(lombardia);
        
        
        assertThat(found).isNotEmpty()
        .allMatch(auto -> auto.getRegione().getNome().equals("Lombardia"));
    }
    
    @Test
    void testFindByStato() {
        Stato stato = statoRepository.findByNome("Disponibile");
        List<Automobile> result = automobileRepository.findByStato(stato);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    void whenFindByStato_ShouldReturnAutomobili() {
        
        Stato disponibile = statoRepository.findByNome("Disponibile");
        
        
        List<Automobile> found = automobileRepository.findByStato(disponibile);
        
        
        assertThat(found).isNotEmpty()
        .allMatch(auto -> auto.getStato().getNome().equals("Disponibile"));
    }
    
    @Test
    void testFindByAlimentazione() {
        Alimentazione alimentazione = alimentazioneRepository.findByNome("Benzina");
        List<Automobile> result = automobileRepository.findByAlimentazione(alimentazione);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    void whenFindByAlimentazione_ShouldReturnAutomobili() {
        
        Alimentazione benzina = alimentazioneRepository.findByNome("Benzina");
        
        
        List<Automobile> found = automobileRepository.findByAlimentazione(benzina);
        
        
        assertThat(found).isNotEmpty()
        .allMatch(auto -> auto.getAlimentazione().getNome().equals("Benzina"));
    }
    
    @Test
    void testFindByPrezzoBetween() {
        List<Automobile> result = automobileRepository.findByPrezzoBetween(5000, 20000);
        assertThat(result).isNotEmpty();
    }
    
    @Test
    void findByPrezzoBetween_ShouldReturnCarsInPriceRange() {
        
        List<Automobile> found = automobileRepository.findByPrezzoBetween(10000.00, 20000.00);
        
        
        assertThat(found).isNotEmpty()
        .allMatch(auto -> 
        auto.getPrezzo().compareTo(new BigDecimal("10000.00")) >= 0 &&
        auto.getPrezzo().compareTo(new BigDecimal("20000.00")) <= 0
        );
    }
    
    @Test
    void testFindByMarcaNotFound() {
        Marca marca = marcaRepository.findByNome("Tesla"); 
        assertThat(marca).isNull(); 
        
        List<Automobile> result = automobileRepository.findByMarca(marca);
        assertThat(result).isEmpty();
        
        
    }
    
    @Test
    void saveCar_ShouldPersistData() {
        
        Marca bmw = marcaRepository.findByNome("BMW");
        Modello serie1 = modelloRepository.findByMarca(bmw)
        .stream()
        .filter(m -> m.getNome().equals("Serie 1"))
        .findFirst()
        .orElseThrow();
        Automobile newAuto = new Automobile();
        newAuto.setAnno(2023);
        newAuto.setPrezzo(new BigDecimal("35000.00"));
        newAuto.setKm(0);
        newAuto.setUtente(utenteTest1);
        newAuto.setMarca(bmw);
        newAuto.setModello(serie1);
        newAuto.setRegione(regioneRepository.findByNome("Lombardia"));
        newAuto.setStato(statoRepository.findByNome("Disponibile"));
        newAuto.setAlimentazione(alimentazioneRepository.findByNome("Diesel"));
        
        
        Automobile saved = automobileRepository.save(newAuto);
        
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMarca().getNome()).isEqualTo("BMW");
        assertThat(saved.getModello().getNome()).isEqualTo("Serie 1");
    }
    
    
    
    @Test
    void testDeleteAutomobile(){
        Iterable<Automobile> automobili = automobileRepository.findAll();
        Automobile auto = automobili.iterator().next();
        automobileRepository.delete(auto);
        assertThat(automobileRepository.findAll()).hasSize(1);
    }
    
    @Test
    void testUpdateAutomobile(){
        Iterable<Automobile> automobili = automobileRepository.findAll();
        Automobile auto = automobili.iterator().next();
        auto.setKm(100);
        automobileRepository.save(auto);
        
        assertThat(automobileRepository.findById(auto.getId())).get()
        .extracting("Km")
        .isEqualTo(100);
    }
}
