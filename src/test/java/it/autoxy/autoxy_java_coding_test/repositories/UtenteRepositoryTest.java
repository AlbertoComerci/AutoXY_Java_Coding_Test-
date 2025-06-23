package it.autoxy.autoxy_java_coding_test.repositories;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import it.autoxy.autoxy_java_coding_test.models.Utente;

// @DataJpaTest: Configura un ambiente di test solo per lo strato di persistenza (JPA).
@DataJpaTest
@Transactional
// @Sql: Esegue lo script SQL per popolare il DB prima di eseguire i test in questa classe.
@Sql("/sql/test-data.sql")
public class UtenteRepositoryTest {

    // Inietta solo il repository che vogliamo testare.
    @Autowired
    private UtenteRepository utenteRepository;

    // NON serve un metodo @BeforeEach. I dati sono già pronti grazie a @Sql.

    @Test
    void findByEmail_ShouldReturnUser_WhenEmailExists() {
        // ARRANGE: Sappiamo che questo utente esiste grazie a test-data.sql
        String existingEmail = "test@example.com";

        // ACT: Eseguiamo la ricerca
        Optional<Utente> foundUtenteOpt = utenteRepository.findByEmail(existingEmail);

        // ASSERT: Verifichiamo che l'Optional non sia vuoto e che i dati siano corretti
        assertThat(foundUtenteOpt).isPresent();
        assertThat(foundUtenteOpt.get().getNome()).isEqualTo("Test");
        assertThat(foundUtenteOpt.get().getCognome()).isEqualTo("User");
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        // ARRANGE
        String nonExistingEmail = "non-esiste@example.com";

        // ACT
        Optional<Utente> foundUtenteOpt = utenteRepository.findByEmail(nonExistingEmail);

        // ASSERT
        assertThat(foundUtenteOpt).isNotPresent(); // o .isEmpty()
    }

    @Test
    void save_ShouldPersistNewUser() {
        // ARRANGE: Creiamo un nuovo utente non ancora salvato
        Utente newUser = new Utente();
        newUser.setNome("Franco");
        newUser.setCognome("Verdi");
        newUser.setEmail("franco.v@example.com");
        newUser.setPassword("supersecret");

        long countBefore = utenteRepository.count();

        // ACT: Salviamo il nuovo utente
        Utente savedUser = utenteRepository.save(newUser);

        // ASSERT: Verifichiamo che il salvataggio sia avvenuto correttamente
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull(); // L'ID deve essere stato generato
        assertThat(savedUser.getNome()).isEqualTo("Franco");
        assertThat(utenteRepository.count()).isEqualTo(countBefore + 1);
    }
    
    @Test
    void delete_ShouldRemoveUser() {
        // ARRANGE: Prendiamo l'ID di un utente che sappiamo esistere
        Optional<Utente> userToDeleteOpt = utenteRepository.findByEmail("test@example.com");
        assertThat(userToDeleteOpt).isPresent();
        Long userId = userToDeleteOpt.get().getId();

        long countBefore = utenteRepository.count();

        // ACT: Cancelliamo l'utente
        utenteRepository.deleteById(userId);

        // ASSERT: Verifichiamo che il numero di utenti sia diminuito e che l'utente non sia più trovabile
        assertThat(utenteRepository.count()).isEqualTo(countBefore - 1);
        assertThat(utenteRepository.findById(userId)).isNotPresent();
    }
    
    @Test
    void update_ShouldModifyExistingUser() {
        // ARRANGE: Troviamo l'utente esistente
        Optional<Utente> userToUpdateOpt = utenteRepository.findByEmail("test@example.com");
        assertThat(userToUpdateOpt).isPresent();
        Utente userToUpdate = userToUpdateOpt.get();

        // ACT: Modifichiamo un campo e salviamo
        userToUpdate.setCognome("Verdi");
        utenteRepository.save(userToUpdate);

        // ASSERT: Ricarichiamo l'utente dal DB e verifichiamo che la modifica sia stata salvata
        Optional<Utente> updatedUserOpt = utenteRepository.findById(userToUpdate.getId());
        assertThat(updatedUserOpt).isPresent();
        assertThat(updatedUserOpt.get().getCognome()).isEqualTo("Verdi");
    }
}