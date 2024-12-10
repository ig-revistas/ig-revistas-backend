package ar.edu.unq.gurpo2.revistas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.edu.unq.gurpo2.revistas.dto.ReservaDto;
import ar.edu.unq.gurpo2.revistas.model.Estado;
import ar.edu.unq.gurpo2.revistas.model.EstadoReserva;
import ar.edu.unq.gurpo2.revistas.model.Reserva;
import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.repository.ReservaRepository;
import ar.edu.unq.gurpo2.revistas.repository.RevistaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {

	@Autowired
	public ReservaRepository reservaRepository;
	
	@Autowired
	public RevistaRepository revistaRepository;
	@Transactional
	public String addReserva(Reserva reservaInfo) {
		try {
			String idReserva = UUID.randomUUID().toString();
			reservaInfo.setId(idReserva);

			reservaRepository.save(reservaInfo);
		} catch (Exception e) {
			return "Error al agregar la reserva: " + e.getMessage();
		}

		return "Se agrego con exito";
	}
	@Transactional
	public boolean existeReservaYNoEsRechazada(String idUsuario, String idReserva) {
	    return reservaRepository.existsByUsuarioIdAndRevistaIdAndEstadoNot(idUsuario, idReserva, EstadoReserva.RECHAZADA);
	}
	public Optional<Reserva> getReservaById(String idReserva) {
		return reservaRepository.findById(idReserva);
	}
	@Transactional
	public String aprobarReserva(Reserva reserva, Integer tiempoVigente) {
		reserva.setEstado(EstadoReserva.APROBADA);
		reserva.setTiempoVigente(tiempoVigente);
		reserva.setFechaAprobacion(LocalDate.now());
		
		Optional<Revista> optionalRevista= revistaRepository.findRevistaById(reserva.getRevista().getId());
		if(!optionalRevista.isPresent()) {
			return "Revista no encontrada.";
		}
		optionalRevista.get().tomarUnaRevista();
		if(optionalRevista.get().getCantidadDisponible()<1) {
		optionalRevista.get().setEstado(Estado.AGOTADA);
		}
		reservaRepository.save(reserva);
        return "Reserva aprobada exitosamente.";
		
	}

	@Transactional
	public String rechazarReserva(Reserva reserva) {
		reserva.setEstado(EstadoReserva.RECHAZADA);
		reserva.setFechaRechazo(LocalDate.now());
		reservaRepository.save(reserva);
		
        return "Reserva rechazada exitosamente.";
	}
	@Transactional
	public List<Reserva> getAllReserva() {
	    return this.reservaRepository.findAll();
	}
	@Transactional
	public List<Reserva> getAllReservaPendiente() {
		return  this.reservaRepository.findReservasWithRevistaAndUsuarioByEstado(EstadoReserva.PENDIENTE);
	}
	
	@Transactional
	public List<Reserva> getAllReservaAprobada() {
		return  this.reservaRepository.findReservasWithRevistaAndUsuarioByEstado(EstadoReserva.APROBADA);
	}
	
	@Transactional
	public String devolverReserva(String reservaId) {
	    Reserva reserva = reservaRepository.findById(reservaId)
	            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	    if (!EstadoReserva.APROBADA.equals(reserva.getEstado())) {
	        return "La reserva no está aprobada.";
	    }
	    reserva.setEstado(EstadoReserva.DEVUELTA); 
	    reservaRepository.save(reserva);
	    Revista revista = reserva.getRevista();
	    int nuevasCopiasDisponibles = revista.getCantidadDisponible() + 1;
	    revista.setCantidadDisponible(nuevasCopiasDisponibles);
	    if (Estado.AGOTADA.equals(revista.getEstado()) && nuevasCopiasDisponibles > 0) {
	        revista.setEstado(Estado.DISPONIBLE);
	    }

	    revistaRepository.save(revista);

	    return "Reserva devuelta con éxito. La revista ahora tiene " + nuevasCopiasDisponibles + " copias disponibles.";
	}
	
	@Transactional
	public ResponseEntity<List<ReservaDto>> obtenerReservasPorUsuario(String idUsuario) {
		List<Reserva> reservas = reservaRepository.findByUsuarioId(idUsuario);
		ResponseEntity<List<ReservaDto>> mensajeReserva = ResponseEntity.ok(reservas.stream().map(ReservaDto::new).collect(Collectors.toList()));
	
		return mensajeReserva;
	}
	public long getCantidadDeReservas() {
	
		return this.reservaRepository.count();
	}
	
	
}


