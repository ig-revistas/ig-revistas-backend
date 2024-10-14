USE revistas;

CREATE TABLE user_info (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL
);

CREATE TABLE Revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    fecha_publicacion DATE,
    categoria VARCHAR(50) NOT NULL,
    editorial VARCHAR(100),
    estado ENUM('DISPONIBLE', 'PRESTADA', 'RESERVADA', 'DEVUELTA') NOT NULL,
    cantidad_disponible INT NOT NULL,
    descripcion TEXT
);

CREATE TABLE Reserva (
    id_revista INT,
    id_usuario INT,
    PRIMARY KEY (id_revista, id_usuario),
    FOREIGN KEY (id_revista) REFERENCES Revista(id),
    FOREIGN KEY (id_usuario) REFERENCES user_info(id_usuario) 
);
create table rol (
	id int auto_increment primary key,
	nombre varchar(50) not null
);

CREATE TABLE Usuario_Rol (
    id_usuario INT,
    id_rol int NOT NULL, 
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES user_info(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id)
);

	
select * from revistas.usuario_rol ur ; 

insert into revistas.rol(nombre) values ('ADMIN_ROLE');
insert into revistas.rol(nombre) values ('USER_ROLE');

insert into revistas.usuario_rol (id_usuario,id_rol) 
values ((select id_usuario from revistas.user_info where email = 'akopach05@gmail.com'),
        (select id from revistas.rol where nombre = 'ADMIN_ROLE'));
       
select * from revistas.revista r ;



