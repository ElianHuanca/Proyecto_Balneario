-- Database: balnearioDB

-- DROP DATABASE IF EXISTS "balnearioDB";

CREATE DATABASE "balnearioDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Bolivia.1252'
    LC_CTYPE = 'Spanish_Bolivia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	
CREATE TABLE usuarios(
	id serial not null,
	ci varchar(15),
	nombre varchar(50),
	email varchar(100),
	password varchar(50),
	fecha_nacimiento Date,
	rol varchar(25)
	CONSTRAINT PK_USUARIOS PRIMARY KEY(id)
)
