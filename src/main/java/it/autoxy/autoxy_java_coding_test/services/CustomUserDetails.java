package it.autoxy.autoxy_java_coding_test.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.autoxy.autoxy_java_coding_test.models.Utente;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//? Questa classe contiene tutti i metodi che indicano se un utente è attivo in piattaforma poiché indicano se l’account non è scaduto, se le credenziali non sono scadute, se 
//? l’account non è bloccato e se è abilitato
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    
    private Long id;
    private String fullname;
    private String email;
    private String password;
    
    public CustomUserDetails(Utente utente) {
        this.id = utente.getId();
        this.fullname = utente.getNome() + " " + utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
    }
    
    //? la Collection può contenere oggetti di qualsiasi classe che estende o implementa l'interfaccia GrantedAuthority
    //? authorities è l'insieme di autorizzazioni o ruoli di un utente
    
    //? restituisce l'insieme di autorizzazioni o ruoli di un utente
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullname;
    }
    
}
