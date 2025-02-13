package it.autoxy.autoxy_java_coding_test.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "stati")
public class Stato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "stato", cascade = CascadeType.ALL)
    private List<Automobile> automobili;
    
}

