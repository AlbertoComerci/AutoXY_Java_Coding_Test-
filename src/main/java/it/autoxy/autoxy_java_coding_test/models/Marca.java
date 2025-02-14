package it.autoxy.autoxy_java_coding_test.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "marche")
public class Marca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
    private List<Modello> modelli = new ArrayList<Modello>();

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
    private List<Automobile> automobili = new ArrayList<Automobile>();
    
}
