package ar.edu.unq.gurpo2.revistas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.gurpo2.revistas.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, String> {
    Optional<Rol> findByNombre(String nombre);
}
