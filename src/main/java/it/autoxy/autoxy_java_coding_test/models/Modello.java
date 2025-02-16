package it.autoxy.autoxy_java_coding_test.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "modelli")
public class Modello {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @OneToMany(mappedBy = "modello", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Automobile> automobili = new ArrayList<Automobile>();

}
