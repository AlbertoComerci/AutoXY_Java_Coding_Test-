package it.autoxy.autoxy_java_coding_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//? Questa classe rappresenta un'eccezione personalizzata che viene lanciata quando una risorsa non viene trovata

// Questa annotazione è opzionale ma aiuta a dare un valore di default
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Costruttore che accetta un messaggio di errore
    public ResourceNotFoundException(String message) {
        // Passa il messaggio al costruttore della classe padre (RuntimeException)
        super(message);
    }
}