package it.autoxy.autoxy_java_coding_test.repositories;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import it.autoxy.autoxy_java_coding_test.models.Alimentazione;
import it.autoxy.autoxy_java_coding_test.models.Automobile;
import it.autoxy.autoxy_java_coding_test.models.Marca;
import it.autoxy.autoxy_java_coding_test.models.Modello;
import it.autoxy.autoxy_java_coding_test.models.Regione;
import it.autoxy.autoxy_java_coding_test.models.Stato;
import it.autoxy.autoxy_java_coding_test.models.Utente;

@DataJpaTest
@Transactional
// Esegue lo script SQL per popolare il DB prima di ogni test in questa classe
@Sql("/sql/test-data.sql")
public class AutomobileRepositoryTest {
    
    // Inietta i repository reali che parleranno con il DB di test H2
    @Autowired private AutomobileRepository automobileRepository;
    @Autowired private MarcaRepository marcaRepository;
    @Autowired private ModelloRepository modelloRepository;
    @Autowired private RegioneRepository regioneRepository;
    @Autowired private StatoRepository statoRepository;
    @Autowired private AlimentazioneRepository alimentazioneRepository;
    @Autowired private UtenteRepository utenteRepository;

    @Test
    void testFindByUtente() {
        // ARRANGE: Recupera l'utente dai dati di test
        Optional<Utente> utenteOpt = utenteRepository.findByEmail("test@example.com");
        assertThat(utenteOpt).isPresent();

        // ACT: Esegui la query
        List<Automobile> result = automobileRepository.findByUtente(utenteOpt.get());

        // ASSERT: Controlla che il risultato non sia vuoto e che tutte le auto appartengano a quell'utente
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(4); // Sappiamo che ci sono 4 auto nel nostro script
        assertThat(result).allMatch(auto -> auto.getUtente().getId() == utenteOpt.get().getId());
    }
    
    @Test
    void testFindByMarca() {
        // ARRANGE
        Optional<Marca> marcaOpt = marcaRepository.findByNome("Fiat");
        assertThat(marcaOpt).isPresent();

        // ACT
        List<Automobile> result = automobileRepository.findByMarca(marcaOpt.get());

        // ASSERT
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result).allMatch(auto -> auto.getMarca().getNome().equals("Fiat"));
    }

    @Test
    void testFindByModello() {
        // ARRANGE
        Optional<Modello> modelloOpt = modelloRepository.findByNome("Panda");
        assertThat(modelloOpt).isPresent();

        // ACT
        List<Automobile> result = automobileRepository.findByModello(modelloOpt.get());

        // ASSERT
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result).allMatch(auto -> auto.getModello().getNome().equals("Panda"));
    }
    
    @Test
    void testFindByRegione() {
        // ARRANGE: CORRETTO - Cerca la regione che esiste nei dati di test
        Optional<Regione> regioneOpt = regioneRepository.findByNome("Piemonte");
        assertThat(regioneOpt).isPresent();

        // ACT
        List<Automobile> result = automobileRepository.findByRegione(regioneOpt.get());

        // ASSERT
        assertThat(result).isNotEmpty().hasSize(4);
        assertThat(result).allMatch(auto -> auto.getRegione().getNome().equals("Piemonte"));
    }
    
    @Test
    void testFindByStato() {
        // ARRANGE: CORRETTO - Cerca lo stato che esiste
        Optional<Stato> statoOpt = statoRepository.findByNome("Usato");
        assertThat(statoOpt).isPresent();

        // ACT
        List<Automobile> result = automobileRepository.findByStato(statoOpt.get());

        // ASSERT
        assertThat(result).isNotEmpty().hasSize(4);
        assertThat(result).allMatch(auto -> auto.getStato().getNome().equals("Usato"));
    }
    
    @Test
    void testFindByAlimentazione() {
        // ARRANGE: CORRETTO - Cerca l'alimentazione che esiste
        Optional<Alimentazione> alimentazioneOpt = alimentazioneRepository.findByNome("Benzina");
        assertThat(alimentazioneOpt).isPresent();

        // ACT
        List<Automobile> result = automobileRepository.findByAlimentazione(alimentazioneOpt.get());
        
        // ASSERT
        assertThat(result).isNotEmpty().hasSize(4);
        assertThat(result).allMatch(auto -> auto.getAlimentazione().getNome().equals("Benzina"));
    }
    
    @Test
    void testFindByPrezzoBetween() {
        // ACT
        List<Automobile> result = automobileRepository.findByPrezzoBetween(new BigDecimal("10000.00"), new BigDecimal("20000.00"));

        // ASSERT: CORRETTO - Cerca le auto che sappiamo esistere in quel range
        assertThat(result).isNotEmpty().hasSize(2); // La Panda da 15k e quella da 12k
        assertThat(result).allMatch(auto ->
            auto.getPrezzo().compareTo(new BigDecimal("10000.00")) >= 0 &&
            auto.getPrezzo().compareTo(new BigDecimal("20000.00")) <= 0
        );
    }
    
    @Test
    void testFindByMarcaNotFound() {
        // ARRANGE
        Optional<Marca> marcaOpt = marcaRepository.findByNome("Tesla"); 
        
        // ASSERT
        assertThat(marcaOpt).isNotPresent(); // Verifichiamo che la marca non esista
        // Poiché la marca è null, non possiamo passarla al repository, il test finisce qui.
    }
    
    @Test
    void saveCar_ShouldPersistData() {
        // ARRANGE: Recupera le entità esistenti per creare una nuova auto
        Optional<Utente> utenteOpt = utenteRepository.findByEmail("test@example.com");
        Optional<Marca> marcaOpt = marcaRepository.findByNome("BMW");
        // CORREZIONE: Cerca il modello che esiste nel nostro data.sql
        Optional<Modello> modelloOpt = modelloRepository.findByNome("Serie 3");
        Optional<Regione> regioneOpt = regioneRepository.findByNome("Piemonte");
        Optional<Stato> statoOpt = statoRepository.findByNome("Usato");
        Optional<Alimentazione> alimentazioneOpt = alimentazioneRepository.findByNome("Benzina");

        // Assicurati che tutti i pezzi esistano prima di costruire l'auto
        assertThat(utenteOpt).isPresent();
        assertThat(marcaOpt).isPresent();
        assertThat(modelloOpt).isPresent();
        assertThat(regioneOpt).isPresent();
        assertThat(statoOpt).isPresent();
        assertThat(alimentazioneOpt).isPresent();

        long countBefore = automobileRepository.count();

        Automobile newAuto = new Automobile();
        newAuto.setAnno(2024);
        newAuto.setPrezzo(new BigDecimal("50000.00"));
        newAuto.setKm(100);
        newAuto.setUtente(utenteOpt.get());
        newAuto.setMarca(marcaOpt.get());
        newAuto.setModello(modelloOpt.get());
        newAuto.setRegione(regioneOpt.get());
        newAuto.setStato(statoOpt.get());
        newAuto.setAlimentazione(alimentazioneOpt.get());
        
        // ACT
        Automobile saved = automobileRepository.save(newAuto);
        
        // ASSERT
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(automobileRepository.count()).isEqualTo(countBefore + 1);
        assertThat(saved.getMarca().getNome()).isEqualTo("BMW");
    }
    
    @Test
    void testDeleteAutomobile(){
        // ARRANGE
        long countBefore = automobileRepository.count();
        assertThat(countBefore).isGreaterThan(0);

        // ACT
        automobileRepository.deleteById(101L); // Cancelliamo un'auto specifica
        
        // ASSERT
        long countAfter = automobileRepository.count();
        assertThat(countAfter).isEqualTo(countBefore - 1);
        assertThat(automobileRepository.findById(101L)).isNotPresent();
    }

    @Test
    void testUpdateAutomobile(){
        // ARRANGE: Recupera un'entità esistente da modificare
        Optional<Automobile> autoOpt = automobileRepository.findById(102L); // Prendiamo la BMW
        assertThat(autoOpt).isPresent();
        Automobile autoToUpdate = autoOpt.get();
        
        // ACT: Modifica un valore e salva
        autoToUpdate.setKm(99999);
        automobileRepository.save(autoToUpdate);
        
        // ASSERT: Ricarica l'entità dal DB e verifica che la modifica sia stata salvata
        Optional<Automobile> updatedAutoOpt = automobileRepository.findById(102L);
        assertThat(updatedAutoOpt).isPresent();
        assertThat(updatedAutoOpt.get().getKm()).isEqualTo(99999);
    }
}