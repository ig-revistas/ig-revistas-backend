# Proyecto de Gestión de Revistas

## Requisitos

Antes de comenzar, asegúrate de tener instalados los siguientes requisitos en tu máquina:

- **Java** (versión compatible con el proyecto)
- **MySQL** o **MariaDB**
- **Git**

## Instrucciones para levantar la base de datos

1. **Crear la base de datos**: 

   Debes crear una base de datos llamada `revistas` en tu servidor de base de datos.
   La contraseña de tu localhost debe ser **"12345678"** y el usuario **"root"**.
   En el repositorio hay una carpeta llamada scripts, con esa puedes crear las tablas,
   luego de levantar el proyecto java estas listo para poder probarlo.

   Para crear la base de datos, usa el siguiente comando en tu cliente MySQL:

   ```sql
   CREATE DATABASE revistas;
