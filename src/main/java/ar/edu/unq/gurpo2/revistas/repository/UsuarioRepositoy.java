package ar.edu.unq.gurpo2.revistas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.gurpo2.revistas.model.Usuario;

@Repository
public interface UsuarioRepositoy extends JpaRepository<Usuario, String> {

	@EntityGraph(attributePaths = "roles")
	Optional<Usuario> findByEmail(String email);

	@EntityGraph(attributePaths = {"reservas", "reservas.revista"})

	Optional<Usuario> findById(String id);
	
}