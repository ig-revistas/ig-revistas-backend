USE revistas;

-- Eliminar las tablas existentes si ya existen
DROP TABLE IF EXISTS Usuario_Rol;
DROP TABLE IF EXISTS Reserva;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS revista;
DROP TABLE IF EXISTS rol;

-- Crear tablas con UUID como tipo de id

-- Tabla de usuarios (UUID generado por Spring, solo almacenado aquí)
CREATE TABLE usuario (
    id CHAR(36) PRIMARY KEY,  
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL,
    portada_url VARCHAR(255)  -- Nueva columna para la portada de usuario
);

-- Tabla de revistas con UUID como id
CREATE TABLE revista (
    id CHAR(36) PRIMARY KEY,  -- Cambiar a CHAR(36) para UUID
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    fecha_publicacion DATE,
    categoria VARCHAR(50) NOT NULL,
    editorial VARCHAR(100),
    estado ENUM('DISPONIBLE', 'PRESTADA', 'RESERVADA', 'DEVUELTA') NOT NULL,
    cantidad_disponible INT NOT NULL,
    descripcion TEXT,
    portada_url VARCHAR(255) 
);

-- Tabla de reservas con UUID
CREATE TABLE Reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_revista Char(36),
    id_usuario CHAR(36),  
    tiempo_vigente INT,
    fecha_pedir_reserva DATE,
    fecha_aprobacion DATE,
    fecha_rechazo DATE,
    estado ENUM('PENDIENTE', 'APROBADA', 'RECHAZADA'),
    FOREIGN KEY (id_revista) REFERENCES Revista(id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);


-- Tabla de roles
CREATE TABLE rol (
    id CHAR(36) PRIMARY KEY,  -- Cambiar a CHAR(36) para UUID
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

-- Asignar rol de administrador al usuario con correo 'akopach05@gmail.com'
-- solo es necesario cambiar el email si deseas otro usuario
INSERT INTO revistas.Usuario_Rol (id_usuario, id_rol) 
VALUES (
    (SELECT id FROM revistas.usuario WHERE email = 'Akopach05@gmail.com'),
    (SELECT id FROM revistas.rol WHERE nombre = 'OPERADOR_ROLE')
);

-- Consultar las revistas
SELECT * FROM revistas.revista;
SELECT * FROM revistas.usuario_rol ur;
SELECT * FROM revistas.rol r;
SELECT * FROM usuario;
select * from revistas.reserva r ;

UPDATE  revistas.rol SET nombre = 'hola';

INSERT INTO revistas.rol(id, nombre) VALUES (UUID(),'OPERADOR_ROLE');

-- Agregar la columna para la portada de usuario si no existe
ALTER TABLE usuario 
ADD COLUMN portada_url VARCHAR(255);

-- Agregar la columna de portada en la tabla revista si no existe
ALTER TABLE revista 
ADD COLUMN portada_url VARCHAR(255);
ALTER TABLE reserva ADD COLUMN estado VARCHAR(255);


ALTER TABLE usuario 
ADD COLUMN token_recuperacion VARCHAR(255);
