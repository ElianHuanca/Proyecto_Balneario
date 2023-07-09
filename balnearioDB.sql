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
  rol varchar(25),
  CONSTRAINT PK_USUARIOS PRIMARY KEY(id)
);

INSERT INTO usuarios VALUES(1,'9648312','Elian Huanca','huancacori@gmail.com','123456','2000-05-02','Administrador');
INSERT INTO usuarios VALUES(2,'7538307','Alvaro Arispe','Alvaro_Ar@gmail.com','123456','1998-12-04','Empleado');
INSERT INTO usuarios VALUES(3,'9648307','Isela Huanca','isela_huanca@gmail.com','123456','1995-03-25','Cliente');

CREATE TABLE tiposMembresias(
  id serial not null,
  nombre varchar(100),
  descripcion text,
  precio FLOAT,
  tiempoDuracion varchar(50),
  CONSTRAINT PK_TIPOSMEMBRESIAS PRIMARY KEY(id)
);
INSERT INTO tiposMembresias VALUES(1,'BRONCE','10 Entradas',150,'3 Meses');
INSERT INTO tiposMembresias VALUES(2,'PLATA','15 Entradas, Descuento 30% De Alquier de ambiente',400,'6 Meses');
INSERT INTO tiposMembresias VALUES(3,'ORO','20 Entradas, Descuento 30% De Alquier de ambiente, Descuento 40% De Consumi de Comida y Bebidas',800,'9 Meses');

CREATE TABLE membresias(
  id serial not null,
  fecha_ini DATE,
  fecha_fin DATE,
  idUsuario int,
  idTipoMembresia int,
  CONSTRAINT PK_MEMBRESIAS PRIMARY KEY(id),
  CONSTRAINT FK_USUARIOS FOREIGN KEY(idUsuario) REFERENCES usuarios(id),
  CONSTRAINT FK_TIPOSMEMBRESIAS FOREIGN KEY(idTipoMembresia) REFERENCES tiposMembresias(id)
);

INSERT INTO membresias VALUES(1,'2023-06-30','2023-09-30',3,1);
INSERT INTO membresias VALUES(2,'2024-09-30','2023-03-30',3,2);

CREATE TABLE ambientes(
  id serial not null,
  nombre varchar(150),
  /*descripcion varchar(250),*/
  precio float,
  capacidad int,
  CONSTRAINT PK_AMBIENTES PRIMARY KEY(id)
);

INSERT INTO ambientes VALUES(1,'Piscina',500,120);
INSERT INTO ambientes VALUES(2,'Jacuzzi',150,12);
INSERT INTO ambientes VALUES(3,'Sauna a Vapor',190,25);
INSERT INTO ambientes VALUES(4,'Sauna Seco',175,25);
INSERT INTO ambientes VALUES(5,'Churrasquera',40,10);

CREATE TABLE reservas(
  id serial not null,
  fecha DATE,
  turno varchar(50),
  idUsuario int,
  CONSTRAINT PK_RESERVAS PRIMARY KEY(id),
  CONSTRAINT FK_USUARIOS FOREIGN KEY(idUsuario) REFERENCES usuarios(id)
);

INSERT INTO reservas VALUES(1,'2023-06-30','Mañana',3);
INSERT INTO reservas VALUES(2,'2023-07-05','Noche',3);

CREATE TABLE detalle_reservas(
  id serial not null,
  idReserva int,
  idAmbiente int,
  CONSTRAINT PK_DETALLE_RESERVAS PRIMARY KEY(id),
  CONSTRAINT FK_RESERVAS FOREIGN KEY(idReserva) REFERENCES reservas(id),
  CONSTRAINT FK_AMBIENTES FOREIGN KEY(idAmbiente) REFERENCES ambientes(id)
);

INSERT INTO detalle_reservas VALUES(1,1,1);
INSERT INTO detalle_reservas VALUES(2,1,2);
INSERT INTO detalle_reservas VALUES(3,2,3);
INSERT INTO detalle_reservas VALUES(4,2,4);

CREATE TABLE productos(
  id serial not null,
  nombre varchar(30),
  descripcion varchar(100),
  precio FLOAT,
  CONSTRAINT PK_PRODUCTOS PRIMARY KEY(id)
);

INSERT INTO productos VALUES(1,'Bolsa De Carbon','',10);
INSERT INTO productos VALUES(2,'1Lt De Detergente Antigrasas','',15);
INSERT INTO productos VALUES(3,'Ramo De Eucalipto','',8);
INSERT INTO productos VALUES(4,'Garrafon De Gas','',18);

CREATE TABLE mantenimientos(
  id serial not null,
  nombre varchar(100),
  montoTotal FLOAT,
  CONSTRAINT PK_MANTENIMIENTOS PRIMARY KEY(id)
);

INSERT INTO mantenimientos VALUES(1,'Mantenimiento A La Churrasquera',25);
INSERT INTO mantenimientos VALUES(2,'Mantenimiento A La Sauna A Vapor',26);

CREATE TABLE usos(
	id serial not null,
	idMantenimiento int not null,
	idProducto int not null,
	CONSTRAINT PK_USOS PRIMARY KEY(id),
	CONSTRAINT FK_MANTENIMIENTOS FOREIGN KEY(idMantenimiento) REFERENCES mantenimientos(id),
	CONSTRAINT FK_PRODUCTOS FOREIGN KEY(idProducto) REFERENCES productos(id)
);

INSERT INTO usos VALUES(1,1,1);
INSERT INTO usos VALUES(2,1,2);
INSERT INTO usos VALUES(3,2,3);
INSERT INTO usos VALUES(4,2,4);

CREATE TABLE realizaciones(
	id serial not null,
	idMantenimiento int,
	idAmbiente int,
	fecha DATE,
	CONSTRAINT PK_REALIZACIONES PRIMARY KEY(id),
	CONSTRAINT FK_MANTENIMIENTOS FOREIGN KEY(idMantenimiento) REFERENCES mantenimientos(id),
	CONSTRAINT FK_AMBIENTES FOREIGN KEY(idAmbiente) REFERENCES ambientes(id)
);

INSERT INTO realizaciones VALUES(1,1,5,'2023-07-07');
INSERT INTO realizaciones VALUES(2,2,3,'2023-07-08');
