-- Script de inicialización del schema para Oracle 10g
-- Ejecutar con usuario DBA antes de levantar la aplicación

-- Crear usuario de la aplicación
CREATE USER territorial_dev
    IDENTIFIED BY dev123
    DEFAULT TABLESPACE USERS
    TEMPORARY TABLESPACE TEMP
    QUOTA UNLIMITED ON USERS;

-- Otorgar privilegios mínimos necesarios
GRANT CONNECT, RESOURCE TO territorial_dev;
GRANT CREATE SESSION      TO territorial_dev;
GRANT CREATE TABLE        TO territorial_dev;
GRANT CREATE SEQUENCE     TO territorial_dev;
GRANT CREATE VIEW         TO territorial_dev;
GRANT CREATE INDEX        TO territorial_dev;
GRANT CREATE TRIGGER      TO territorial_dev;

-- Para Flyway (necesita leer el schema history)
GRANT SELECT, INSERT, UPDATE, DELETE ON territorial_dev.flyway_schema_history TO territorial_dev;

-- Tablespace adicional (opcional, ajustar según ambiente)
-- CREATE TABLESPACE TBS_TERRITORIAL
--     DATAFILE '/oradata/ORCL/territorial01.dbf' SIZE 100M AUTOEXTEND ON NEXT 10M MAXSIZE 500M;

COMMIT;
