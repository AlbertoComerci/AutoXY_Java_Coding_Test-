package it.autoxy.autoxy_java_coding_test.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class AutomobileModelUnitTest {
    @Test
    void testAutomobileBuilderAndEquals() {
        Automobile auto1 = new Automobile();
        auto1.setAnno(2022);
        auto1.setPrezzo(new BigDecimal("12345.67"));
        auto1.setKm(10000);
        Automobile auto2 = new Automobile();
        auto2.setAnno(2022);
        auto2.setPrezzo(new BigDecimal("12345.67"));
        auto2.setKm(10000);
        assertEquals(auto1.getAnno(), auto2.getAnno());
        assertEquals(auto1.getPrezzo(), auto2.getPrezzo());
        assertEquals(auto1.getKm(), auto2.getKm());
    }

    @Test
    void testNullSafeSetters() {
        Automobile auto = new Automobile();
        auto.setPrezzo(null);
        assertNull(auto.getPrezzo());
    }
}
