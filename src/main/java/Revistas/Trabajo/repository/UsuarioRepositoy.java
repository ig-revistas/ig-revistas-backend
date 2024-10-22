package Revistas.Trabajo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Revistas.Trabajo.model.Usuario;

@Repository
public interface UsuarioRepositoy extends JpaRepository<Usuario, String> {
    
    @EntityGraph(attributePaths = "roles")  
    Optional<Usuario> findByEmail(String email); 
}
