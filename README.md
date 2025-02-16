AutoXY Java Coding Test 


API REST con operazioni CRUD per gestire catalogo di automobili

## 📌 Tecnologie Utilizzate

+ Java 21
+ Spring Web (per creare l'API REST)
+ Spring Data JPA (per la persistenza dei dati)
+ MySQL
+ Spring Security
+ Spring Boot DevTools
+ Lombok
+ Spring Boot Starter Test (per i test unitari e di integrazione)
+ Postman (per il test delle API)
+ SpringDoc OpenAPI (per la documentazione API) (OpenAPI/Swagger)


1️⃣ Clonare il repository

git clone git@github.com:AlbertoComerci/AutoXY_Java_Coding_Test-.git

2️⃣ Configurare il database

- Creazione del database (io ho usato MySQL)
- Collegare il database all'IDE (io ho usato l'estenzione SQLtools per VSCODE)
- Connettere il database al progetto (ho usato il driver jdbc)
- Popolare il database con i file SQL presenti (il create e poi l'insert)

3️⃣ Test

in:
src>test>java>it>autoxy>auto_java_coding_test>

Sono presenti alcuni test unitari sulle repository (cartella repositories)
e alcuni test di integrazione sul CRUD lato controller e service (cartella controllers)



🛠️ API Endpoints

📌 Automobili

Metodo                      	            Endpoint	                                Descrizione

GET	                                     /api/automobili	                        Restituisce tutte le auto
GET	                                     /api/automobili/{id}	                    Dettaglio auto
POST	                                 /api/automobili	                        Aggiunge una nuova auto
PUT	                                     /api/automobili/{id}	                    Modifica un'auto
DELETE	                                 /api/automobili/{id}	                    Elimina un'auto
GET	                                     /api/automobili/utente/{id}	            Filtra auto per utente
GET	                                     /api/automobili/marca/{id}	                Filtra auto per marca
GET	                                     /api/automobili/modello/{id}	            Filtra auto per modello
GET	                                     /api/automobili/regione/{id}	            Filtra auto per regione
GET	                                     /api/automobili/stato/{id}	                Filtra auto per stato
GET	                                     /api/automobili/alimentazione/{id}	        Filtra auto per alimentazione
GET	                                     /api/automobili/prezzo	                    Filtra auto per range di prezzo


Per il filtro sul range di prezzo un esempio di URL completo è: http://localhost:8080/api/automobili/prezzo?prezzoMin=10000&prezzoMax=30000

Invece un esempio di JSON per la POST e la PUT è questo:

{
    "anno": 2020,
    "prezzo": 15000.50,
    "km": 50000,
    "utenteId": 1,
    "marcaId": 2,
    "modelloId": 5,
    "regioneId": 3,
    "statoId": 1,
    "alimentazioneId": 4
}


📌 Utenti

GET	                                     /api/utenti	                        Restituisce tutti gli utenti
POST	                                 /api/utenti/register	                   Salva un nuovo utente


Gli altri endpoint presenti nel progetto o non sono stati ancora testati o non funzionano come dovrebbero.


## Diagramma ER

docs> autoxy_test_diagramma_002