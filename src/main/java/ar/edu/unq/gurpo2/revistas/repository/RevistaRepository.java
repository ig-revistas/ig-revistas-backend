package ar.edu.unq.gurpo2.revistas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unq.gurpo2.revistas.model.Revista;

public interface RevistaRepository extends JpaRepository<Revista, String> {

	 Optional<Revista> findRevistaById(String id);
	 
	 @EntityGraph(value = "RevistaWithEstdo", type = EntityGraph.EntityGraphType.LOAD)
	 Optional<Revista> findRevistaWithEstadoById(String id);
  
}
