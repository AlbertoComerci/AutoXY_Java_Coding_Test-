# AutoXY Java Coding Test

## Descrizione del Progetto

AutoXY è una REST API sviluppata in Java con Spring Boot per la gestione di un catalogo di automobili. Il progetto consente operazioni CRUD (Create, Read, Update, Delete) su automobili e utenti, con filtri avanzati e autenticazione. È pensato per essere facilmente estendibile e testabile, con una struttura chiara e separazione delle responsabilità.

---

## Tecnologie Utilizzate

- **Java 21**
- **Spring Boot** (Web, Data JPA, Security, DevTools)
- **MySQL** (database relazionale)
- **Lombok** (per ridurre il boilerplate)
- **Spring Boot Starter Test** (JUnit 5, Mockito)
- **SpringDoc OpenAPI** (documentazione Swagger)
- **Postman** (test manuali delle API)

---

## Struttura del Progetto

├── pom.xml
├── README.md
├── mvnw / mvnw.cmd
├── autoxyJavaTest.session.sql
├── docs/
│   └── autoxy_test_diagramma_002.png   # Diagramma ER
├── sql/
│   ├── create.sql                     # Script creazione tabelle
│   ├── drop.sql                       # Script drop tabelle
│   └── insert.sql                     # Script popolamento dati
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── it/autoxy/autoxy_java_coding_test/
│   │   │       ├── AutoxyJavaCodingTestApplication.java
│   │   │       ├── config/            # Configurazioni (es. Security)
│   │   │       ├── controllers/       # Controller REST
│   │   │       ├── dtos/              # Data Transfer Object
│   │   │       ├── models/            # Entità JPA
│   │   │       ├── repositories/      # Repository JPA
│   │   │       └── services/          # Logica di business
│   │   └── resources/
│   │       ├── application.properties # Configurazione DB e app
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── it/autoxy/autoxy_java_coding_test/
│               ├── controllers/       # Test controller (mock)
│               ├── models/            # Test unitari modelli
│               ├── repositories/      # Test repository (mock)
│               └── services/          # Test servizi (mock)
└── target/                            # Output build
```

---

## Setup del Progetto

### 1️⃣ Clonazione del Repository

```bash
git clone git@github.com:AlbertoComerci/AutoXY_Java_Coding_Test-.git
```

### 2️⃣ Configurazione Database

- Creare un database MySQL (es: `autoxy_db`)
- Eseguire gli script in `sql/create.sql` e `sql/insert.sql` per popolare le tabelle
- Configurare le credenziali in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/autoxy_db
spring.datasource.username=tuo_utente
spring.datasource.password=la_tua_password
```

### 3️⃣ Avvio Applicazione

- Da terminale:

```bash
./mvnw spring-boot:run
```

- Oppure da IDE (Esegui `AutoxyJavaCodingTestApplication`)

---

## API Endpoints

### 📌 Automobili

| Metodo | Endpoint                                 | Descrizione                        |
|--------|------------------------------------------|------------------------------------|
| GET    | /api/automobili                         | Restituisce tutte le auto          |
| GET    | /api/automobili/{id}                    | Dettaglio auto                     |
| POST   | /api/automobili                         | Aggiunge una nuova auto            |
| PUT    | /api/automobili/{id}                    | Modifica un'auto                   |
| DELETE | /api/automobili/{id}                    | Elimina un'auto                    |
| GET    | /api/automobili/utente/{id}             | Filtra auto per utente             |
| GET    | /api/automobili/marca/{id}              | Filtra auto per marca              |
| GET    | /api/automobili/modello/{id}            | Filtra auto per modello            |
| GET    | /api/automobili/regione/{id}            | Filtra auto per regione            |
| GET    | /api/automobili/stato/{id}              | Filtra auto per stato              |
| GET    | /api/automobili/alimentazione/{id}      | Filtra auto per alimentazione      |
| GET    | /api/automobili/prezzo?prezzoMin=10000&prezzoMax=30000 | Filtra per range di prezzo |

#### Esempio JSON per POST/PUT

```json
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
```

### 📌 Utenti

| Metodo | Endpoint                | Descrizione              |
|--------|-------------------------|--------------------------|
| GET    | /api/utenti            | Restituisce tutti gli utenti |
| POST   | /api/utenti/register    | Registra un nuovo utente |

---

## Autenticazione & Sicurezza

- L'autenticazione è gestita tramite Spring Security.
- Alcuni endpoint sono pubblici (es. registrazione utente), altri richiedono autenticazione.
- Le password sono salvate in modo sicuro (hash).

---

## Testing

- I test unitari sono scritti con JUnit 5 e Mockito e **non dipendono dal database**.
- I test di integrazione usano un database H2 in memoria.
- Per eseguire tutti i test:

```bash
./mvnw test
```

---

## Documentazione API

- La documentazione OpenAPI/Swagger è disponibile all'avvio su:

```
http://localhost:8080/swagger-ui.html
```

---

## Diagramma ER

Il diagramma Entità-Relazione si trova in `docs/autoxy_test_diagramma_002.png`.

---

## Note e Best Practice

- Gestione centralizzata dei dati null e delle eccezioni
- Separazione chiara tra controller, servizi, repository e modelli
- Utilizzo di DTO per l'esposizione delle API
- Codice pulito, testabile e facilmente estendibile

---

## Autore

Alberto Comerci

---

Per qualsiasi domanda o contributo, apri una issue o una pull request sul repository GitHub.