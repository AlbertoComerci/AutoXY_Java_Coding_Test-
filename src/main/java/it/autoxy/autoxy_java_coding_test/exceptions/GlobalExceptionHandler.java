package it.autoxy.autoxy_java_coding_test.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//? Questa classe speciale intercetterà le eccezioni lanciate dai nostri controller e le trasformerà in risposte JSON pulite per il client

// @ControllerAdvice dice a Spring che questa classe intercetterà eccezioni da tutti i controller.
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler dice: "Quando viene lanciata una ResourceNotFoundException, esegui questo metodo".
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        
        // Creiamo un corpo della risposta JSON strutturato
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.NOT_FOUND.value()); // es. 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage()); // Il messaggio specifico dell'eccezione
        body.put("path", request.getDescription(false).replace("uri=", "")); // L'URL che ha causato l'errore

        // Restituiamo una ResponseEntity con il corpo JSON e lo stato HTTP corretto
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    // In futuro si potranno aggiungere qui altri metodi @ExceptionHandler per gestire
    // altri tipi di eccezioni (es. per la validazione, errori di business, etc.)
}