package it.autoxy.autoxy_java_coding_test.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "automobili")
public class Automobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int anno;

    //BigDecimal mi permette di essere preciso con i numeri dopo la virgola
    //In questo modo corrisponde al DECIMAL(10, 2) nella query SQL
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;

    @Column(nullable = false)
    private int km;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "modello_id", nullable = false)
    private Modello modello;

    @ManyToOne
    @JoinColumn(name = "regione_id", nullable = false)
    private Regione regione;

    @ManyToOne
    @JoinColumn(name = "stato_id", nullable = false)
    private Stato stato;

    @ManyToOne
    @JoinColumn(name = "alimentazione_id", nullable = false)
    private Alimentazione alimentazione;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();

}

