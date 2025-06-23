package it.autoxy.autoxy_java_coding_test.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtenteModelUnitTest {
    @Test
    void testUtenteBuilderAndEquals() {
        Utente u1 = new Utente();
        u1.setNome("Mario"); // Corretto
        u1.setCognome("Rossi"); // Corretto
        u1.setEmail("mario@test.it");
        u1.setPassword("pw");
        
        Utente u2 = new Utente();
        u2.setNome("Mario");
        u2.setCognome("Rossi");
        u2.setEmail("mario@test.it");
        u2.setPassword("pw");
        
        assertEquals(u1.getNome(), u2.getNome());
        assertEquals(u1.getCognome(), u2.getCognome());
        assertEquals(u1.getEmail(), u2.getEmail());
        assertEquals(u1.getPassword(), u2.getPassword());
    }
    
    @Test
    void testNullSafeSetters() {
        Utente u = new Utente();
        u.setEmail(null);
        assertNull(u.getEmail());
    }
    
    @Test
    void testUtenteEquals() {
        Utente u1 = new Utente();
        u1.setId(1L); // L'equals si basa spesso sull'ID
        u1.setEmail("test@test.it");
        
        Utente u2 = new Utente();
        u2.setId(1L);
        u2.setEmail("test@test.it");
        
        assertEquals(u1, u2); // Questo testa il tuo metodo equals()
    }
}
