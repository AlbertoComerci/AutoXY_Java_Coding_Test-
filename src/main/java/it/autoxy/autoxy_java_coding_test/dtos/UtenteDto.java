package it.autoxy.autoxy_java_coding_test.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {

    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String cognome;

    @NotEmpty(message = "L'email non può essere vuota")
    @Email
    private String email;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;
    
}
