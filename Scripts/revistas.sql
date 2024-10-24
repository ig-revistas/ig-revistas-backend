USE revistas;



-- Eliminar tablas existentes (en orden para evitar conflictos con claves foráneas)
DROP TABLE IF EXISTS Usuario_Rol;
DROP TABLE IF EXISTS Reserva;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS Revista;
DROP TABLE IF EXISTS rol;

-- Crear tablas de nuevo

-- Tabla de usuarios (UUID generado por Spring, solo almacenado aquí)
CREATE TABLE usuario (
    id CHAR(36) PRIMARY KEY,  
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL
);

-- Tabla de revistas
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

-- Tabla de reservas (relación muchos a muchos entre usuarios y revistas)
CREATE TABLE Reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_revista INT,
    id_usuario CHAR(36),  
    FOREIGN KEY (id_revista) REFERENCES Revista(id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

-- Tabla de roles
CREATE TABLE rol (
    id CHAR(36) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);
-- Tabla para la relación entre usuarios y roles (muchos a muchos)
CREATE TABLE Usuario_Rol (
    id_usuario CHAR(36),  
    id_rol CHAR(36) NOT NULL, 
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_rol) REFERENCES rol(id)
);

-- Consultas adicionales
SELECT * FROM revistas.Usuario_Rol;

-- Insertar roles
INSERT INTO revistas.rol(nombre) VALUES ('ADMIN_ROLE');
INSERT INTO revistas.rol(nombre) VALUES ('USER_ROLE');

-- Asignar rol de administrador al usuario con correo 'akopach05@gmail.com'
-- solo es necesario cambiar el email si deseas otro usuario
INSERT INTO revistas.Usuario_Rol (id_usuario, id_rol) 
VALUES ((SELECT id_usuario FROM revistas.usuario WHERE email = 'akopach05@gmail.com'),
        (SELECT id FROM revistas.rol WHERE nombre = 'ADMIN_ROLE'));

-- Consultar las revistas
SELECT * FROM revistas.Revista;
select * from revistas.usuario_rol ur ;
select * from revistas.rol r ;

update  revistas.rol set nombre = 'hola';


