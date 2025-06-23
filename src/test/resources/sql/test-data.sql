-- Inserisce dati di test prevedibili
INSERT INTO utenti (id, nome, cognome, email, password) VALUES
(1, 'Test', 'User', 'test@example.com', '$2a$10$GiseI.F5sC3C.45aRME3/eL9xJ5nPh51y2k51y.2Gqg49Y0b5b2nS'); -- password: "password"

INSERT INTO marche (id, nome) VALUES
(1, 'Fiat'),
(2, 'BMW');

INSERT INTO modelli (id, nome, marca_id) VALUES
(1, 'Panda', 1),
(2, 'Serie 3', 2);

INSERT INTO regioni (id, nome) VALUES (1, 'Piemonte');
INSERT INTO stati (id, nome) VALUES (1, 'Usato');
INSERT INTO alimentazioni (id, nome) VALUES (1, 'Benzina');

INSERT INTO automobili (id, anno, prezzo, km, utente_id, marca_id, modello_id, regione_id, stato_id, alimentazione_id) VALUES
(101, 2020, 15000.00, 50000, 1, 1, 1, 1, 1, 1), -- Fiat Panda
(102, 2021, 35000.00, 25000, 1, 2, 2, 1, 1, 1), -- BMW Serie 3
(103, 2019, 12000.00, 80000, 1, 1, 1, 1, 1, 1), -- Altra Fiat Panda
(104, 2022, 45000.00, 10000, 1, 2, 2, 1, 1, 1); -- Altra BMW Serie 3