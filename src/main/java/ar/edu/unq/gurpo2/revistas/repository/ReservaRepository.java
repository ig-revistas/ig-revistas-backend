package ar.edu.unq.gurpo2.revistas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unq.gurpo2.revistas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
    boolean existsByUsuarioIdAndRevistaId(String idUsuario, String idRevista);

}
