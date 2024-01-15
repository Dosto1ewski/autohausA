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

-- docker compose exec postgres bash
-- psql --dbname=autohaus --username=autohaus [--file=/sql/V1.0__Create.sql]

-- https://www.postgresql.org/docs/current/sql-createtype.html
-- https://www.postgresql.org/docs/current/datatype-enum.html
-- CREATE TYPE geschlecht AS ENUM ('MAENNLICH', 'WEIBLICH', 'DIVERS');

CREATE TABLE IF NOT EXISTS adresse (
    id        uuid PRIMARY KEY USING INDEX TABLESPACE autohausspace,
    plz       char(5) NOT NULL CHECK (plz ~ '\d{5}'),
    ort       varchar(40) NOT NULL
) TABLESPACE autohausspace;
CREATE INDEX IF NOT EXISTS adresse_plz_idx ON adresse(plz) TABLESPACE autohausspace;

CREATE TABLE IF NOT EXISTS autohaus (
    id            uuid PRIMARY KEY USING INDEX TABLESPACE autohausspace,
    version       integer NOT NULL DEFAULT 0,
    name          varchar(40) NOT NULL,
    homepage      varchar(40),
    adresse_id    uuid NOT NULL UNIQUE USING INDEX TABLESPACE autohausspace REFERENCES adresse,
    erzeugt       timestamp NOT NULL,
    aktualisiert  timestamp NOT NULL
) TABLESPACE autohausspace;

CREATE INDEX IF NOT EXISTS autohaus_name_idx ON autohaus(name) TABLESPACE autohausspace;

CREATE TABLE IF NOT EXISTS mitarbeiter (
    id        uuid PRIMARY KEY USING INDEX TABLESPACE autohausspace,
    vorname      varchar(40) NOT NULL,
    nachname     varchar(40) NOT NULL,
    position     varchar(10) CHECK (position ~ 'SEKRETAER|MANAGER|VERKAEUFER'),
    autohaus_id  uuid REFERENCES autohaus,
    idx          integer NOT NULL DEFAULT 0
) TABLESPACE autohausspace;
CREATE INDEX IF NOT EXISTS mitarbeiter_autohaus_id_idx ON mitarbeiter(autohaus_id) TABLESPACE autohausspace;

CREATE TABLE IF NOT EXISTS parkplatz (
    id            uuid PRIMARY KEY USING INDEX TABLESPACE autohausspace,
    name          varchar(40) NOT NULL,
    maxKapazitaet integer NOT NULL DEFAULT 0,
    autohaus_id   uuid REFERENCES autohaus,
    idx           integer NOT NULL DEFAULT 0
) TABLESPACE autohausspace;
CREATE INDEX IF NOT EXISTS parkplatz_autohaus_id_idx ON parkplatz(autohaus_id) TABLESPACE autohausspace;
