package ar.edu.unq.gurpo2.revistas.repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.gurpo2.revistas.model.EstadoReserva;
import ar.edu.unq.gurpo2.revistas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
    
	@EntityGraph(value = "ReservaWithRevistaAndUsuario", type = EntityGraph.EntityGraphType.LOAD)
	List<Reserva> findReservasWithRevistaAndUsuarioByEstado(EstadoReserva estado);
    
    @EntityGraph(value = "ReservaWithUsuario", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Reserva> findById(String id);

    @EntityGraph(value = "ReservaWithRevista", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Reserva> findByRevistaId(String idRevista);

    @EntityGraph(value = "ReservaWithRevistaAndUsuario", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Reserva> findByRevistaIdAndUsuarioId(String idRevista, String idUsuario);

	boolean existsByUsuarioIdAndRevistaIdAndEstadoNot(String idUsuario, String idReserva, EstadoReserva estado);
	

}
