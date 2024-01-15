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
    kapazitaet integer NOT NULL DEFAULT 0,
    autohaus_id   uuid REFERENCES autohaus,
    idx           integer NOT NULL DEFAULT 0
) TABLESPACE autohausspace;
CREATE INDEX IF NOT EXISTS parkplatz_autohaus_id_idx ON parkplatz(autohaus_id) TABLESPACE autohausspace;
