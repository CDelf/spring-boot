-- Départements
INSERT INTO departement (id, nom, code) VALUES (1, 'Ain', '01');
INSERT INTO departement (id, nom, code) VALUES (2, 'Aisne', '02');
INSERT INTO departement (id, nom, code) VALUES (3, 'Allier', '03');
INSERT INTO departement (id, nom, code) VALUES (4, 'Alpes-de-Haute-Provence', '04');
INSERT INTO departement (id, nom, code) VALUES (5, 'Hautes-Alpes', '05');

-- Villes pour chaque département (10 par département)

-- Département 1
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (101, 'Bourg-en-Bresse', 41000, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (102, 'Oyonnax', 23000, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (103, 'Ambérieu-en-Bugey', 14000, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (104, 'Bellegarde-sur-Valserine', 12000, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (105, 'Gex', 11000, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (106, 'Miribel', 9500, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (107, 'Ferney-Voltaire', 9300, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (108, 'Saint-Genis-Pouilly', 9200, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (109, 'Divonne-les-Bains', 9100, 1);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (110, 'Reyrieux', 9000, 1);

-- Département 2
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (201, 'Laon', 25000, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (202, 'Saint-Quentin', 55000, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (203, 'Soissons', 29000, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (204, 'Château-Thierry', 15000, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (205, 'Tergnier', 14000, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (206, 'Hirson', 9500, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (207, 'Fère-en-Tardenois', 8500, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (208, 'Guise', 8400, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (209, 'Chauny', 8300, 2);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (210, 'Vervins', 8200, 2);

-- Département 3
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (301, 'Moulins', 20000, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (302, 'Montluçon', 35000, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (303, 'Vichy', 24000, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (304, 'Cusset', 13500, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (305, 'Yzeure', 13000, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (306, 'Commentry', 10000, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (307, 'Gannat', 9800, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (308, 'Saint-Pourçain-sur-Sioule', 9700, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (309, 'Varennes-sur-Allier', 9600, 3);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (310, 'Domérat', 9500, 3);

-- Département 4
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (401, 'Digne-les-Bains', 18000, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (402, 'Manosque', 22000, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (403, 'Sisteron', 7700, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (404, 'Forcalquier', 4500, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (405, 'Les Mées', 3800, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (406, 'Oraison', 3700, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (407, 'Volx', 3600, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (408, 'Gréoux-les-Bains', 3500, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (409, 'Pierrevert', 3400, 4);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (410, 'Mane', 3300, 4);

-- Département 5
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (501, 'Gap', 40000, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (502, 'Briançon', 12000, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (503, 'Embrun', 6000, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (504, 'Laragne-Montéglin', 3500, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (505, 'Veynes', 3300, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (506, 'Chorges', 3200, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (507, 'L’Argentière-la-Bessée', 3100, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (508, 'Guillestre', 3000, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (509, 'La Saulce', 2900, 5);
INSERT INTO ville (id, nom, nb_habitants, id_dept) VALUES (510, 'Le Monêtier-les-Bains', 2800, 5);
