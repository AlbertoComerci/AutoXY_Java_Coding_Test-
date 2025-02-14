package it.autoxy.autoxy_java_coding_test.dtos;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutomobileDto {
    private Long id;
    private int anno;
    private BigDecimal prezzo;
    private int km;
    private String utente;
    private String marca;
    private String modello;
    private String regione;
    private String stato;
    private String alimentazione;
}
