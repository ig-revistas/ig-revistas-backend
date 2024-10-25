package Revistas.Trabajo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import Revistas.Trabajo.model.Rol;

public interface RolRepository extends JpaRepository<Rol, String> {
    Optional<Rol> findByNombre(String nombre);
}
