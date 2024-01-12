-- Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <https://www.gnu.org/licenses/>.

--  docker compose exec postgres bash
--  psql --dbname=kunde --username=kunde [--file=/sql/V1.1__Insert.sql]

-- COPY mit CSV-Dateien erfordert den Pfad src/main/resources/...
-- Dieser Pfad existiert aber nicht im Docker-Image
-- https://www.postgresql.org/docs/current/sql-copy.html

INSERT INTO adresse (id, plz, ort)
VALUES
    ('30000000-0000-0000-0000-000000000000','76131','Karlsruhe'),
    ('30000000-0000-0000-0000-000000000001','70372','Stuttgart'),
    ('30000000-0000-0000-0000-000000000002','60388','Frankfurt'),

INSERT INTO autohaus (id, name, homepage, adresse_id)
VALUES
    -- admin
    ('00000000-0000-0000-0000-000000000000','Yorckhaus','https://www.Yorckhaus.com','30000000-0000-0000-0000-000000000000'),
    ('00000000-0000-0000-0000-000000000001','Benzhaus','https://www.Benzhaus.com','30000000-0000-0000-0000-000000000001'),
    ('00000000-0000-0000-0000-000000000002','Volkshaus','https://www.Benzhaus.com','30000000-0000-0000-0000-000000000002')

INSERT INTO parkplatz (id, name, maxkapazitaet, autohaus_id, idx)
VALUES
    ('10000000-0000-0000-0000-000000000000','Yorckparken',34,'00000000-0000-0000-0000-000000000000',0),
    ('10000000-0000-0000-0000-000000000001','Benzparken',42,'00000000-0000-0000-0000-000000000001',0),
    ('10000000-0000-0000-0000-000000000001','Volksparken',55,'00000000-0000-0000-0000-000000000002',0),

INSERT INTO mitarbeiter (id, vorname, nachname, position, autohaus_id, idx)
VALUES
    ('20000000-0000-0000-0000-000000000000','Lukas','Scholz','MANAGER','00000000-0000-0000-0000-000000000000',0),
    ('20000000-0000-0000-0000-000000000001','Paul','Clauss','SEKRETAER','00000000-0000-0000-0000-000000000000',1),
    ('20000000-0000-0000-0000-000000000002','Heinrich','Precht','VERKAEUFER','00000000-0000-0000-0000-000000000000',2),
    ('20000000-0000-0000-0000-000000000003','Jan','Böhmermann','MANAGER','00000000-0000-0000-0000-000000000001',0),
    ('20000000-0000-0000-0000-000000000004','Paulina','Rehbein','SEKRETAER','00000000-0000-0000-0000-000000000001',1),
    ('20000000-0000-0000-0000-000000000005','Kai','Schmidt','VERKAEUFER','00000000-0000-0000-0000-000000000001',2),
    ('20000000-0000-0000-0000-000000000006','Joseph','Berolth','MANAGER','00000000-0000-0000-0000-000000000002',0),
    ('20000000-0000-0000-0000-000000000007','Mark','Rehbein','SEKRETAER','00000000-0000-0000-0000-000000000002',1),
    ('20000000-0000-0000-0000-000000000008','Melina','Weber','VERKAEUFER','00000000-0000-0000-0000-000000000002',2);
