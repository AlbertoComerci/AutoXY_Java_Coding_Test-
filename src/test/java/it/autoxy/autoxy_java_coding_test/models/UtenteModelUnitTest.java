package it.autoxy.autoxy_java_coding_test.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtenteModelUnitTest {
    @Test
    void testUtenteBuilderAndEquals() {
        Utente u1 = new Utente();
        u1.setUsername("Mario");
        u1.setEmail("mario@test.it");
        u1.setPassword("pw");
        Utente u2 = new Utente();
        u2.setUsername("Mario");
        u2.setEmail("mario@test.it");
        u2.setPassword("pw");
        assertEquals(u1.getUsername(), u2.getUsername());
        assertEquals(u1.getEmail(), u2.getEmail());
        assertEquals(u1.getPassword(), u2.getPassword());
    }

    @Test
    void testNullSafeSetters() {
        Utente u = new Utente();
        u.setEmail(null);
        assertNull(u.getEmail());
    }
}
