--CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS plpgsql;

CREATE TABLE usuarios(
  id serial not null,
  ci varchar(15) unique,
  nombre varchar(50),
  email varchar(100) unique,
  password TEXT,
  fecha_nacimiento Date,
  rol varchar(25),
  CONSTRAINT PK_USUARIOS PRIMARY KEY(id)
);

INSERT INTO usuarios VALUES(1,'9648312','Elian Huanca','huancacori@gmail.com','123456','2000-05-02','Administrador');
INSERT INTO usuarios VALUES(2,'7538307','Aldo Huanca','Aldo_Huanca@gmail.com','123456','1998-12-04','Empleado');
INSERT INTO usuarios VALUES(3,'9648307','Isela Huanca','Isela_Huanca@gmail.com','123456','1995-03-25','Empleado');
INSERT INTO usuarios VALUES(4,'9648313','Diana Paniagua','Diana_Paniagua@gmail.com','123456','1998-04-29','Cliente');
INSERT INTO usuarios VALUES(5,'1648314','Mary Choque','Mary_Choque@gmail.com','123456','1990-01-05','Cliente');
INSERT INTO usuarios VALUES(6,'2648308','Juan Perez','Juan_Perez@gmail.com','123456','1985-02-12','Cliente');
INSERT INTO usuarios VALUES(7,'3648315','Maria Garcia','Maria_Garcia@gmail.com','123456','1975-03-25','Cliente');
INSERT INTO usuarios VALUES(8,'4648316','Carlos Rodriguez','Carlos_Rodriguez@gmail.com','123456','1988-06-12','Cliente');
INSERT INTO usuarios VALUES(9,'5648309','Ana Martinez','Ana_Martinez@gmail.com','123456','1999-03-23','Cliente');
INSERT INTO usuarios VALUES(10,'6648317','Luis Gonzalez','Luis_Gonzalez@gmail.com','123456','1988-07-19','Cliente');
INSERT INTO usuarios VALUES(11,'7648318','Laura Sanchez','Laura_Sanchez@gmail.com','123456','1995-08-17','Cliente');
INSERT INTO usuarios VALUES(12,'8648319','Jose Lopez','Jose_Lopez@gmail.com','123456','1991-09-07','Cliente');
INSERT INTO usuarios VALUES(13,'9648300','Sandra Ramirez','Sandra_Ramirez@gmail.com','123456','1998-07-10','Cliente');

CREATE TABLE tiposMembresias(
	id serial not null,
	nombre varchar(100),
	descripcion text,
	precio FLOAT,
	duracion varchar(50),
	CONSTRAINT PK_TIPOSMEMBRESIAS PRIMARY KEY(id)
);

INSERT INTO tiposMembresias VALUES(1,'BRONCE','10 Entradas',150,'3 Meses');
INSERT INTO tiposMembresias VALUES(2,'PLATA','15 Entradas, Descuento 30% De Alquier de ambiente',400,'6 Meses');
INSERT INTO tiposMembresias VALUES(3,'ORO','20 Entradas, Descuento 30% De Alquier de ambiente, Descuento 40% De Consumi de Comida y Bebidas',800,'9 Meses');

CREATE TABLE pagos(
	id serial not null,
	tipo_pago varchar(50),
	monto_total FLOAT default (0),
	fecha Date,
	CONSTRAINT PK_PAGOS PRIMARY KEY (id)
);

CREATE TABLE membresias(
  id serial not null,
  fecha_ini DATE,
  fecha_fin DATE,
  idUsuario int,
  idTipoMembresia int,
	idPago int,
  CONSTRAINT PK_MEMBRESIAS PRIMARY KEY(id),
  CONSTRAINT FK_USUARIOS FOREIGN KEY(idUsuario) REFERENCES usuarios(id),
  CONSTRAINT FK_PAGOS FOREIGN KEY(idPago) REFERENCES pagos(id),
  CONSTRAINT FK_TIPOSMEMBRESIAS FOREIGN KEY(idTipoMembresia) REFERENCES tiposMembresias(id)
);

CREATE OR REPLACE FUNCTION update_pago_monto()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE pagos
  SET monto_total = monto_total + (SELECT tiposMembresias.precio FROM tiposMembresias WHERE id = NEW.idTipoMembresia) 
  WHERE id = NEW.idPago;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_monto_total
AFTER INSERT ON membresias
FOR EACH ROW
EXECUTE FUNCTION update_pago_monto();

CREATE OR REPLACE FUNCTION insertarPagosMembresias()
RETURNS VOID AS $$
DECLARE 
  id INT;
  idUsuario INT;
  idTipoMembresia INT;
  fecha DATE;
  fechaFin DATE;
  tipoPago VARCHAR(50);
BEGIN
  FOR id IN 1..50 LOOP
    tipoPago := (SELECT valor_aleatorio FROM (VALUES ('QR'), ('Tarjeta Debito'), ('Tarjeta Credito')) AS valores(valor_aleatorio) ORDER BY random() LIMIT 1);
    fecha := (SELECT fecha_aleatoria FROM generate_series('2023-06-01'::date, '2023-08-01'::date, '1 day') AS fecha_aleatoria ORDER BY random() LIMIT 1);
    idTipoMembresia := (SELECT tiposMembresias.id FROM tiposMembresias ORDER BY RANDOM() LIMIT 1);
    
    IF idTipoMembresia = 1 THEN
      fechaFin := fecha + INTERVAL '30 days';	
    ELSIF idTipoMembresia = 2 THEN
      fechaFin := fecha + INTERVAL '60 days';
    ELSIF idTipoMembresia = 3 THEN
      fechaFin := fecha + INTERVAL '90 days';
    END IF;
    
    idUsuario := (SELECT usuarios.id FROM usuarios WHERE usuarios.rol = 'Cliente' ORDER BY RANDOM() LIMIT 1);
    INSERT INTO pagos VALUES (id, tipoPago, 0, fecha);
    INSERT INTO membresias VALUES (id, fecha, fechaFin, idUsuario,idTipoMembresia, id);
  END LOOP;
END;
$$ LANGUAGE plpgsql;
SELECT insertarPagosMembresias();

/*SELECT  tiposMembresias.nombre, COUNT(*) AS cantidad
FROM membresias
JOIN tiposMembresias ON membresias.idTipoMembresia = tiposMembresias.id
GROUP BY tiposMembresias.nombre;*/


CREATE TABLE ambientes(
  id serial not null,
  nombre varchar(150),
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
  idPago int,
  CONSTRAINT PK_RESERVAS PRIMARY KEY(id),
  CONSTRAINT FK_USUARIOS FOREIGN KEY(idUsuario) REFERENCES usuarios(id),
	CONSTRAINT FK_PAGOS FOREIGN KEY(idPago) REFERENCES pagos(id)
);

CREATE OR REPLACE FUNCTION insertarPagosReservas()
RETURNS VOID AS $$
DECLARE 
  id INT;
  idUsuario INT;
  idPago INT;
  fecha DATE;
  turno VARCHAR(50);
  tipoPago VARCHAR(50);
BEGIN
	idPago:=(SELECT MAX(pagos.id) FROM pagos)+1;	
  FOR id IN 1..50 LOOP
    tipoPago := (SELECT valor_aleatorio FROM (VALUES ('QR'), ('Tarjeta Debito'), ('Tarjeta Credito')) AS valores(valor_aleatorio) ORDER BY random() LIMIT 1);
	turno := (SELECT valor_aleatorio FROM (VALUES ('Mañana'), ('Tarde'), ('Noche')) AS valores(valor_aleatorio) ORDER BY random() LIMIT 1);
    fecha := (SELECT fecha_aleatoria FROM generate_series('2023-03-01'::date, '2023-08-01'::date, '1 day') AS fecha_aleatoria ORDER BY random() LIMIT 1);  
    idUsuario := (SELECT usuarios.id FROM usuarios WHERE usuarios.rol = 'Cliente' ORDER BY RANDOM() LIMIT 1);
    INSERT INTO pagos VALUES (idPago, tipoPago, 0, fecha);
    INSERT INTO reservas VALUES (id, fecha,turno, idUsuario, idPago);
	idPago:=idPago+1;
  END LOOP;
END;
$$ LANGUAGE plpgsql;
SELECT insertarPagosReservas();

CREATE TABLE detalle_reservas(
	id serial not null,
	idReserva int,
	idAmbiente int,
	CONSTRAINT PK_DETALLE_RESERVAS PRIMARY KEY(id),
	CONSTRAINT FK_RESERVAS FOREIGN KEY(idReserva) REFERENCES reservas(id),
	CONSTRAINT FK_AMBIENTES FOREIGN KEY(idAmbiente) REFERENCES ambientes(id)
);

CREATE OR REPLACE FUNCTION update_pago_monto2()
RETURNS TRIGGER AS $$
DECLARE
	idPago INT;
BEGIN
	idPago:=(SELECT reservas.idPago FROM reservas WHERE reservas.id=NEW.idReserva);
	UPDATE pagos
	SET monto_total = monto_total + (SELECT ambientes.precio FROM ambientes WHERE ambientes.id = NEW.idAmbiente) 
	WHERE pagos.id=idPago;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_monto_total2
AFTER INSERT ON detalle_reservas
FOR EACH ROW
EXECUTE FUNCTION update_pago_monto2();

CREATE OR REPLACE FUNCTION insertarDetalleReservas()
RETURNS VOID AS $$
DECLARE 
  id INT;idRes INT;idAmb INT;veces INT;
BEGIN
	id:=1;
	FOR idRes IN 1..50 LOOP 		
		veces:=(SELECT floor(random() * 3) + 1 AS random_value);
		WHILE(veces >0) LOOP 
			idAmb:=(SELECT ambientes.id FROM ambientes ORDER BY RANDOM() LIMIT 1);
			IF(SELECT COUNT(*)FROM detalle_reservas WHERE detalle_reservas.idReserva=idRes and detalle_reservas.idAmbiente=idAmb)=0 THEN
				INSERT INTO detalle_reservas VALUES(id,idRes,idAmb);
				id:=id+1;
				veces:=veces-1;
			END IF;
		END LOOP;
	END LOOP;
END;
$$ LANGUAGE plpgsql;
SELECT insertarDetalleReservas();

/*SELECT  ambientes.nombre, COUNT(*) AS cantidad
FROM detalle_reservas
JOIN ambientes ON detalle_reservas.idAmbiente = ambientes.id
GROUP BY ambientes.nombre;*/


CREATE TABLE productos(
	id serial not null,
	nombre varchar(30),
	--descripcion varchar(100),
	precio FLOAT,
	CONSTRAINT PK_PRODUCTOS PRIMARY KEY(id)
);

INSERT INTO productos VALUES(1,'Bolsa De Carbon',10);
INSERT INTO productos VALUES(2,'1Lt De Detergente Antigrasas',15);
INSERT INTO productos VALUES(3,'Ramo De Eucalipto',8);
INSERT INTO productos VALUES(4,'Garrafon De Gas',18);

CREATE TABLE usos(
	id serial not null,	
	fecha Date,
	cantidad int,
	idProducto int,
	idAmbiente int,
	CONSTRAINT PK_USOS PRIMARY KEY(id),
	CONSTRAINT FK_PRODUCTOS FOREIGN KEY(idProducto) REFERENCES productos(id),
	CONSTRAINT FK_AMBIENTES FOREIGN KEY(idAmbiente) REFERENCES ambientes(id)
);

INSERT INTO usos VALUES(1,'2023-09-07',2,1,5);
INSERT INTO usos VALUES(2,'2023-09-07',1,2,5);
INSERT INTO usos VALUES(3,'2023-09-09',4,3,3);
INSERT INTO usos VALUES(4,'2023-09-09',1,4,3);

CREATE TABLE ingresos(
	id serial not null,
	fecha Date,
	--hora varchar(50),
	idUsuario int not null,
	CONSTRAINT PK_INGRESOS PRIMARY KEY (id),
	CONSTRAINT FK_INGRESOS_USUARIO_ID FOREIGN KEY ( idUsuario)
	REFERENCES usuarios (id)
	ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE OR REPLACE FUNCTION insertarIngresos()
RETURNS VOID AS $$
DECLARE 
  id INT;idUsuario INT;fecha DATE;
BEGIN
	FOR id IN 1..50 LOOP
		fecha := (SELECT fecha_aleatoria FROM generate_series('2023-06-01'::date, '2023-08-01'::date, '1 day') AS fecha_aleatoria ORDER BY random() LIMIT 1);
		idUsuario:= (SELECT usuarios.id FROM usuarios WHERE usuarios.rol = 'Cliente' ORDER BY RANDOM() LIMIT 1);
		INSERT INTO ingresos VALUES(id,fecha,idUsuario);
	END LOOP;
END;
$$ LANGUAGE plpgsql;
SELECT insertarIngresos();

CREATE TABLE comandos(
	id serial not null,
	cu varchar(50),
	accion varchar(50),
	parametros text,
	ejemplo text,
	CONSTRAINT PK_COMANDOS PRIMARY KEY (id)
);
	
INSERT INTO comandos VALUES(1,'usuarios','Registrar Usuario','ci, nombre, fecha_nacimiento, email, password, rol','usuarios post [9638521;Maicol;2000-02-02;maicol@gmail.com;123456;cliente]'); 
INSERT INTO comandos VALUES(2,'usuarios','Editar Usuario ','id,ci, nombre, fecha_nacimiento, email, password, rol',' usuarios put [1;12345;Maicol;2000-02-02;maicola@gmail.com;123456;cliente]');
INSERT INTO comandos VALUES(3,'usuarios','Eliminar Usuario','id','usuarios delete [1]');
INSERT INTO comandos VALUES(4,'usuarios','Obtener Usuario','ninguno','usuarios get');
SELECT * FROM usuarios;

INSERT INTO comandos VALUES(5,'tiposMembresias','Registrar tiposMembresias','nombre,descripcion,presio,duracion','tiposmembresias post [Diamante;1000 Boletos;600;12 Meses]');
INSERT INTO comandos VALUES(6,'tiposMembresias','Editar tiposMembresias','id,nombre,descripcion,precio,duracion','tiposmembresias put [1;Platino;1000 Boletos;600;16 Meses]');
INSERT INTO comandos VALUES(7,'tiposMembresias','Eliminar tiposMembresias','id','tiposmembresias delete [4]');
INSERT INTO comandos VALUES(8,'tiposMembresias','Obtener tiposMembresias','ninguno','tiposmembresias get');
SELECT * FROM tiposMembresias;

INSERT INTO comandos VALUES(9,'MEMBRESIAS','Registrar Membresias','fecha_ini,fecha_fin,idUsuario,idTipoMembresia,idpago','membresias post [2023-02-01;2023-05-01;3;1;1]');
INSERT INTO comandos VALUES(10,'MEMBRESIAS','Editar Membresias','id,fecha_ini,fecha_fin,idUsuario,idTipoMembresia,idpago','membresias put [1;2025-02-01;2025-05-01;3;1;1]');
INSERT INTO comandos VALUES(11,'MEMBRESIAS','Eliminar Membresias','id','membresias delete [51]');
INSERT INTO comandos VALUES(12,'MEMBRESIAS','Obtener Membresias','ninguno','membresias get');
INSERT INTO comandos VALUES(41,'MEMBRESIAS','Grafica De Membresias','ninguno','membresias grafica');
SELECT * FROM membresias;

INSERT INTO comandos VALUES(13,'AMBIENTES','Registrar AMBIENTES','nombre,precio,capacidad','ambientes post [Cancha De Futbol; 100; 12]');
INSERT INTO comandos VALUES(14,'AMBIENTES','Editar AMBIENTES','id,nombre,precio,capacidad','ambientes put [6;Cancha De Tennis; 100; 12]');
INSERT INTO comandos VALUES(15,'AMBIENTES','Eliminar AMBIENTES','id','ambientes delete [6]');
INSERT INTO comandos VALUES(16,'AMBIENTES','Obtener AMBIENTES','ninguno','ambientes get');
INSERT INTO comandos VALUES(42,'AMBIENTES','Grafica De AMBIENTES','ninguno','ambientes grafica');
SELECT * FROM ambientes;

INSERT INTO comandos VALUES(17,'RESERVAS','Registrar RESERVAS','fecha,turno,idUsuario,idPago','reservas post [2023-07-10;Tarde;3;1]');
INSERT INTO comandos VALUES(18,'RESERVAS','Editar RESERVAS','id,fecha,turno,idUsuario,idPago','reservas put [1;2024-07-10;Madrugada;3;1]');
INSERT INTO comandos VALUES(19,'RESERVAS','Eliminar RESERVAS','id','reservas delete [51]');
INSERT INTO comandos VALUES(20,'RESERVAS','Obtener RESERVAS','ninguno','reservas get');
SELECT * FROM reservas;

INSERT INTO comandos VALUES(21,'DETALLE_RESERVAS','Registrar DETALLE_RESERVAS','idReserva,idAmbiente','detallereservas post [1;4]');
INSERT INTO comandos VALUES(22,'DETALLE_RESERVAS','Editar DETALLE_RESERVAS',' id,idReserva,idAmbiente','detallereservas put [1;1;1]');
INSERT INTO comandos VALUES(23,'DETALLE_RESERVAS','Eliminar DETALLE_RESERVAS','id','detallereservas delete [1]');
INSERT INTO comandos VALUES(24,'DETALLE_RESERVAS','Obtener DETALLE_RESERVAS','ninguno','detallereservas get');
SELECT * FROM detalle_reservas;

INSERT INTO comandos VALUES(25,'PRODUCTOS','Registrar PRODUCTOS','nombre,precio ','productos post [Coca Cola 2Lts; 10]');
INSERT INTO comandos VALUES(26,'PRODUCTOS','Editar PRODUCTOS','id,nombre,precio ','productos put [1;Coca Cola 3Lts; 15]');
INSERT INTO comandos VALUES(27,'PRODUCTOS','Eliminar PRODUCTOS','id','productos delete [5]');
INSERT INTO comandos VALUES(28,'PRODUCTOS','Obtener PRODUCTOS','ninguno','productos get');
SELECT * FROM productos;

INSERT INTO comandos VALUES(29,'USOS','Registrar USOS','fecha,cantidad,idAmbiente,idProducto','usos post [2023-07-10;4;4;1]');
INSERT INTO comandos VALUES(30,'USOS','Editar USOS','id,fecha,cantidad,idAmbiente,idProducto','usos put [1;2024-07-10;8;2;2]');
INSERT INTO comandos VALUES(31,'USOS','Eliminar USOS','id','usos delete [5]');
INSERT INTO comandos VALUES(32,'USOS','Obtener USOS','ninguno','usos get');
SELECT * FROM usos;

INSERT INTO comandos VALUES(33,'INGRESOS','Registrar INGRESOS','fecha,idUsuario','ingresos post [2023-07-22;3]');
INSERT INTO comandos VALUES(34,'INGRESOS','Editar INGRESOS','id,fecha,idUsuario','ingresos put [1;2024-07-22;3]');
INSERT INTO comandos VALUES(35,'INGRESOS','Eliminar INGRESOS','id','ingresos delete [1]');
INSERT INTO comandos VALUES(36,'INGRESOS','Obtener INGRESOS','ninguno','ingresos get');
SELECT * FROM ingresos; 

INSERT INTO comandos VALUES(37,'PAGOS','Registrar PAGOS','tipo_pago,monto_total,fecha','pagos post [Tarjeta Debito;300;2023-07-22]');
INSERT INTO comandos VALUES(38,'PAGOS','Editar PAGOS','id,tipo_pago,monto_total,fecha','pagos put [100;Tarjeta Debito;1000;2023-07-22]');
INSERT INTO comandos VALUES(39,'PAGOS','Eliminar PAGOS','id','pagos delete [101]');
INSERT INTO comandos VALUES(40,'PAGOS','Obtener PAGOS','ninguno','pagos get');
SELECT * FROM pagos;
SELECT * FROM comandos;


SELECT setval(pg_get_serial_sequence('usuarios', 'id'), coalesce(max(id), 0) + 1, false) FROM usuarios;
SELECT setval(pg_get_serial_sequence('tiposMembresias', 'id'), coalesce(max(id), 0) + 1, false) FROM tiposMembresias;
SELECT setval(pg_get_serial_sequence('membresias', 'id'), coalesce(max(id), 0) + 1, false) FROM membresias;
SELECT setval(pg_get_serial_sequence('ambientes', 'id'), coalesce(max(id), 0) + 1, false) FROM ambientes;
SELECT setval(pg_get_serial_sequence('reservas', 'id'), coalesce(max(id), 0) + 1, false) FROM reservas;
SELECT setval(pg_get_serial_sequence('detalle_reservas', 'id'), coalesce(max(id), 0) + 1, false) FROM detalle_reservas;
SELECT setval(pg_get_serial_sequence('productos', 'id'), coalesce(max(id), 0) + 1, false) FROM productos;
SELECT setval(pg_get_serial_sequence('usos', 'id'), coalesce(max(id), 0) + 1, false) FROM usos;
SELECT setval(pg_get_serial_sequence('ingresos', 'id'), coalesce(max(id), 0) + 1, false) FROM ingresos;
SELECT setval(pg_get_serial_sequence('pagos', 'id'), coalesce(max(id), 0) + 1, false) FROM pagos;
SELECT setval(pg_get_serial_sequence('comandos', 'id'), coalesce(max(id), 0) + 1, false) FROM comandos;

DROP TABLE ingresos;
DROP TABLE usos;
DROP TABLE productos;
DROP TABLE detalle_reservas;
DROP TABLE reservas;
DROP TABLE ambientes;
DROP TABLE membresias;
DROP TABLE pagos;
DROP TABLE tiposMembresias;
DROP TABLE usuarios;
DROP TABLE comandos;