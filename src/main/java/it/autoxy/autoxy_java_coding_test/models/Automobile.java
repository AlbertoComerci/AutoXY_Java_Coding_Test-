package it.autoxy.autoxy_java_coding_test.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
// @Getter 
// @Setter 
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
    @JsonBackReference
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    @JsonBackReference 
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "modello_id", nullable = false)
    @JsonBackReference 
    private Modello modello;

    @ManyToOne
    @JoinColumn(name = "regione_id", nullable = false)
    @JsonBackReference 
    private Regione regione;

    @ManyToOne
    @JoinColumn(name = "stato_id", nullable = false)
    @JsonBackReference 
    private Stato stato;

    @ManyToOne
    @JoinColumn(name = "alimentazione_id", nullable = false)
    @JsonBackReference 
    private Alimentazione alimentazione;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modello getModello() {
        return modello;
    }

    public void setModello(Modello modello) {
        this.modello = modello;
    }

    public Regione getRegione() {
        return regione;
    }

    public void setRegione(Regione regione) {
        this.regione = regione;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(Alimentazione alimentazione) {
        this.alimentazione = alimentazione;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

