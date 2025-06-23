INSERT INTO stati (nome) VALUES
('Disponibile'),
('Venduta');

INSERT INTO alimentazioni (nome) VALUES
('Benzina'),
('Diesel'),
('Elettrica'),
('GPL'),
('Ibrida'),
('Metano');

INSERT INTO marche (nome) VALUES
('Fiat'),
('Ford'),
('Volkswagen'),
('BMW'),
('Mercedes-Benz'),
('Audi'),
('Toyota'),
('Honda'),
('Renault'),
('Peugeot');

INSERT INTO regioni (nome) VALUES
('Abruzzo'),
("Valle d'Aosta"),
('Piemonte'),
('Lombardia'),
('Trentino-Alto-Adige'),
('Veneto'),
('Friuli-Venezia Giulia'),
('Liguria'),
('Emilia-Romagna'),
('Toscana'),
('Umbria'),
('Marche'),
('Lazio'),
('Molise'),
('Campania'),
('Puglia'),
('Basilicata'),
('Calabria'),
('Sicilia'),
('Sardegna');

-- Fiat
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Fiat'), 'Panda'),
((SELECT id FROM Marche WHERE nome = 'Fiat'), '500'),
((SELECT id FROM Marche WHERE nome = 'Fiat'), 'Punto'),
((SELECT id FROM Marche WHERE nome = 'Fiat'), 'Tipo'),
((SELECT id FROM Marche WHERE nome = 'Fiat'), 'Bravo');

-- Ford
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Ford'), 'Fiesta'),
((SELECT id FROM Marche WHERE nome = 'Ford'), 'Focus'),
((SELECT id FROM Marche WHERE nome = 'Ford'), 'Mustang'),
((SELECT id FROM Marche WHERE nome = 'Ford'), 'Kuga'),
((SELECT id FROM Marche WHERE nome = 'Ford'), 'Puma');

-- Volkswagen
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Volkswagen'), 'Golf'),
((SELECT id FROM Marche WHERE nome = 'Volkswagen'), 'Polo'),
((SELECT id FROM Marche WHERE nome = 'Volkswagen'), 'Passat'),
((SELECT id FROM Marche WHERE nome = 'Volkswagen'), 'Tiguan'),
((SELECT id FROM Marche WHERE nome = 'Volkswagen'), 'T-Roc');

-- BMW
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'BMW'), 'Serie 1'),
((SELECT id FROM Marche WHERE nome = 'BMW'), 'Serie 3'),
((SELECT id FROM Marche WHERE nome = 'BMW'), 'Serie 5'),
((SELECT id FROM Marche WHERE nome = 'BMW'), 'X1'),
((SELECT id FROM Marche WHERE nome = 'BMW'), 'X3');

-- Mercedes-Benz
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Mercedes-Benz'), 'Classe A'),
((SELECT id FROM Marche WHERE nome = 'Mercedes-Benz'), 'Classe C'),
((SELECT id FROM Marche WHERE nome = 'Mercedes-Benz'), 'Classe E'),
((SELECT id FROM Marche WHERE nome = 'Mercedes-Benz'), 'GLA'),
((SELECT id FROM Marche WHERE nome = 'Mercedes-Benz'), 'GLE');

-- Audi
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Audi'), 'A1'),
((SELECT id FROM Marche WHERE nome = 'Audi'), 'A3'),
((SELECT id FROM Marche WHERE nome = 'Audi'), 'A4'),
((SELECT id FROM Marche WHERE nome = 'Audi'), 'Q3'),
((SELECT id FROM Marche WHERE nome = 'Audi'), 'Q5');

-- Toyota
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Toyota'), 'Yaris'),
((SELECT id FROM Marche WHERE nome = 'Toyota'), 'Corolla'),
((SELECT id FROM Marche WHERE nome = 'Toyota'), 'RAV4'),
((SELECT id FROM Marche WHERE nome = 'Toyota'), 'C-HR'),
((SELECT id FROM Marche WHERE nome = 'Toyota'), 'Hilux');

-- Honda
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Honda'), 'Civic'),
((SELECT id FROM Marche WHERE nome = 'Honda'), 'Accord'),
((SELECT id FROM Marche WHERE nome = 'Honda'), 'HR-V'),
((SELECT id FROM Marche WHERE nome = 'Honda'), 'CR-V'),
((SELECT id FROM Marche WHERE nome = 'Honda'), 'Jazz');

-- Renault
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Renault'), 'Clio'),
((SELECT id FROM Marche WHERE nome = 'Renault'), 'Megane'),
((SELECT id FROM Marche WHERE nome = 'Renault'), 'Captur'),
((SELECT id FROM Marche WHERE nome = 'Renault'), 'Kadjar'),
((SELECT id FROM Marche WHERE nome = 'Renault'), 'Talisman');

-- Peugeot
INSERT INTO modelli (marca_id, nome) VALUES
((SELECT id FROM Marche WHERE nome = 'Peugeot'), '208'),
((SELECT id FROM Marche WHERE nome = 'Peugeot'), '308'),
((SELECT id FROM Marche WHERE nome = 'Peugeot'), '508'),
((SELECT id FROM Marche WHERE nome = 'Peugeot'), '3008'),
((SELECT id FROM Marche WHERE nome = 'Peugeot'), '5008');

-- Utenti
INSERT INTO utenti (nome, cognome, email, password) VALUES
('Mario', 'Rossi', 'mario.rossi@example.com', 'password123'),
('Luca', 'Bianchi', 'luca.bianchi@example.com', 'password456');

-- Automobili
INSERT INTO automobili (anno, prezzo, km, utente_id, marca_id, modello_id, regione_id, stato_id, alimentazione_id) VALUES
(2016, 10000, 10000, 1, 1, 1, 1, 1, 1),
(2017, 20000, 20000, 1, 2, 2, 2, 2, 2);

