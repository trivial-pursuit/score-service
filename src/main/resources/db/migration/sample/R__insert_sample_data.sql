TRUNCATE TABLE scores CASCADE;
TRUNCATE TABLE composers CASCADE;
TRUNCATE TABLE arrangers CASCADE;
TRUNCATE TABLE parts CASCADE;
TRUNCATE TABLE concerts CASCADE;
TRUNCATE TABLE score_part CASCADE;
TRUNCATE TABLE concert_score CASCADE;

-- PARTS
INSERT INTO parts (id, name)
VALUES (1, 'ACCORDION_1'),
       (2, 'ACCORDION_2'),
       (3, 'ACCORDION_3'),
       (4, 'ACCORDION_4'),
       (5, 'BASS'),
       (6, 'ELECTRONIUM'),
       (7, 'KEYBOARD'),
       (8, 'DIATONIC'),
       (9, 'DRUMS'),
       (10, 'TIMPANI'),
       (11, 'FULL_SCORE');

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('parts_id_seq', (SELECT COUNT(1) FROM parts));


-- COMPOSERS
INSERT INTO composers (id, first_name, last_name, day_of_birth, day_of_death)
VALUES (1, 'Wolfgang Amadeus', 'Mozart', '1756-01-27', '1791-12-05'),
       (2, 'Rudolf', 'Würthner', '1920-08-13', '1974-12-03'),
       (3, 'Ron', 'Goodwin', '1925-02-17', '2003-08-01'),
       (4, 'Willy', 'Friedrich', '1910-01-01', '2000-12-12');

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('composers_id_seq', (SELECT COUNT(1) FROM composers));


-- ARRANGERS
INSERT INTO arrangers (id, first_name, last_name, day_of_birth, day_of_death)
VALUES (1, 'Rudolf', 'Würthner', '1920-08-13', '1974-12-03'),
       (2, 'Thomas', 'Bauer', '1960-08-26', null);

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('arrangers_id_seq', (SELECT COUNT(1) FROM arrangers));


-- SCORES
INSERT INTO scores (id, score_number, title, composer_id, arranger_id, publisher)
VALUES (1, 406, 'Zauberflöte (Ouvertüre)', 1, 2, 'Musikverlag Jetelina'),
       (2, 408, 'Variationen über ein russisches Volkslied', 2, null, null),
       (3, 377, 'Miss Marple', 3, null, null),
       (4, 186, 'Aalener Harmonikagrüße', 4, null, null);

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('scores_id_seq', (SELECT COUNT(1) FROM scores));


-- CONCERTS
INSERT INTO concerts (id, name, date, location)
VALUES (1, 'Herbstkonzert', '2020-10-10', 'WeststadtZentrum Aalen'),
       (2, 'Kirchenkonzert', '2020-04-10', 'Salvatorkirche Aalen'),
       (3, 'Gemeinschaftskonzert', '2022-10-10', 'Turn- und Festhalle Dorfmerkingen');

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('concerts_id_seq', (SELECT COUNT(1) FROM concerts));


-- SCORE_PART
INSERT INTO score_part (score_id, part_id, analog, digital)
VALUES (1, 1, true, true),
       (1, 2, true, true),
       (1, 3, true, true),
       (1, 4, true, true),
       (1, 5, true, true),
       (1, 9, true, true),
       (1, 11, true, true),
       (2, 1, true, true),
       (2, 2, true, true),
       (2, 3, true, true),
       (2, 4, true, true),
       (2, 5, true, true),
       (2, 6, true, true),
       (2, 10, true, true),
       (2, 11, true, false),
       (3, 1, true, false),
       (3, 2, true, false),
       (3, 3, true, false),
       (3, 4, true, false),
       (3, 5, true, false),
       (3, 11, true, false),
       (4, 1, false, false),
       (4, 2, false, false),
       (4, 3, false, false),
       (4, 4, false, false),
       (4, 5, false, false),
       (4, 11, false, false);

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('score_part_id_seq', (SELECT COUNT(1) FROM score_part));


-- CONCERT_SCORE
INSERT INTO concert_score (concert_id, score_id, sequence_number)
VALUES (1, 1, 1),
       (1, 2, 2),
       (2, 2, 1),
       (2, 3, 2),
       (3, 4, null);

-- set ID counter to the amount of already inserted rows
SELECT SETVAL('concert_score_id_seq', (SELECT COUNT(1) FROM concert_score));
