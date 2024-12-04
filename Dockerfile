# Vamos a dividir el Dockerfile en varias etapas
###############################################

# Etapa de instalación de dependencias
######################################
# Vamos a partir de una imagen simple
# que tiene maven, y allí instalar
# todas las dependencias de la app
FROM maven:3.9-eclipse-temurin-21 AS dependency_install
# eclipse-temurin está basado en ubuntu, vamos a actualizar
# el sistema por si acaso.
RUN apt-get update && apt-get upgrade -y
# ahora si, instalamos dependencias de maven de la app
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline

# Etapa de correr en modo desarrollo
####################################
# Como partimos de dependency_install
# ya están instaladas las dependencias
# solo debemos copiar el resto de los
# archivos, y luego correr la app
FROM dependency_install AS run_in_dev
COPY src /app/src
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]

# Etapa de correr en modo build
###############################
# Idem, partimos de dependency_install
# pero esta vez solo corremos el build
# Notar que acá no hay app corriendo,
# solo build
FROM dependency_install AS run_build
COPY src /app/src
RUN mvn package -DskipTests


# Etapa de correr el build
##########################
# Ahora lo que tenemos es que
# se buildeo la app, y queremos
# correrla. En lugar de usar mvn,
# como el build ya empaquetó todo
# un jar, necesitamos correr el jar
FROM eclipse-temurin:21-jre-alpine AS serve_build
EXPOSE 8080
COPY --from=run_build /app/target/Trabajo-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "/app/Trabajo-0.0.1-SNAPSHOT.jar"]