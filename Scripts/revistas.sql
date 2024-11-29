-- Seleccionar la base de datos o crearla si es necesario
CREATE DATABASE IF NOT EXISTS revistas;
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
    contrasenia VARCHAR(100) NOT NULL,
    portada_url VARCHAR(255)  -- Nueva columna para la portada de usuario
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
    descripcion TEXT,
    portada_url VARCHAR(255)  -- Columna para la portada de revista
);

-- Tabla de reservas (relación muchos a muchos entre usuarios y revistas)
CREATE TABLE Reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_revista INT,
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

-- Consultas para verificar las tablas
SELECT * FROM Usuario_Rol;
SELECT * FROM Revista;
SELECT * FROM usuario;
SELECT * FROM rol;

-- Asignar rol de administrador al usuario específico (cambiar email si es necesario)
INSERT INTO Usuario_Rol (id_usuario, id_rol) 
VALUES (
    (SELECT id FROM usuario WHERE email = 'eze@gmail.com'),
    (SELECT id FROM rol WHERE nombre = 'ADMIN_ROLE')
);

-- Asignar rol de operador al usuario específico (cambiar email si es necesario)
INSERT INTO Usuario_Rol (id_usuario, id_rol) 
VALUES (
    (SELECT id FROM usuario WHERE email = 'eze@gmail.com'),
    (SELECT id FROM rol WHERE nombre = 'OPERADOR_ROLE')
);

-- Insertar roles si no existen
INSERT INTO rol (id, nombre) VALUES (UUID(), 'ADMIN_ROLE') ON DUPLICATE KEY UPDATE nombre = nombre;
INSERT INTO rol (id, nombre) VALUES (UUID(), 'USER_ROLE') ON DUPLICATE KEY UPDATE nombre = nombre;
INSERT INTO rol (id, nombre) VALUES (UUID(), 'OPERADOR_ROLE') ON DUPLICATE KEY UPDATE nombre = nombre;

-- Actualizar el nombre de un rol
UPDATE rol SET nombre = 'hola';

-- Eliminar todas las filas de Revista (si es necesario)
DELETE FROM Revista;

-- Eliminar el rol específico (si es necesario)
DELETE FROM rol WHERE id = '041de785-9d60-11ef-9b54-3464a90578e7';

-- Agregar columna para portadas si no existe (en caso de que ya esté creada)
ALTER TABLE Revista 
ADD COLUMN IF NOT EXISTS portada_url VARCHAR(255);

-- Agregar columna de portada en la tabla usuario si no existe
ALTER TABLE usuario 
ADD COLUMN IF NOT EXISTS portada_url VARCHAR(255);

-- Agregar columna de tiempo_vigente en la tabla Reserva si no existe
ALTER TABLE Reserva 
ADD COLUMN IF NOT EXISTS tiempo_vigente INT;

-- Eliminar una reserva específica (si es necesario)
DELETE FROM Reserva WHERE id = 4;
