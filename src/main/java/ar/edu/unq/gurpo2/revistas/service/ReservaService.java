package ar.edu.unq.gurpo2.revistas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	public boolean existeReserva(String idUsuario, String string) {
	    return reservaRepository.existsByUsuarioIdAndRevistaId(idUsuario, string);
	}
	public Optional<Reserva> getReservaById(String idReserva) {
		return reservaRepository.findById(idReserva);
	}
	@Transactional
	public String aprobarReserva(Reserva reserva, Integer tiempoVigente) {
		reserva.setEstado("APROBADA");
		reserva.setTiempoVigente(tiempoVigente);
		reserva.setFechaAprobacion(LocalDate.now());
		
		Optional<Revista> optionalRevista= revistaRepository.findRevistaById(reserva.getRevista().getId());
		if(!optionalRevista.isPresent()) {
			return "Revista no encontrada.";
		}
		optionalRevista.get().tomarUnaRevista();
		if(optionalRevista.get().getCantidadDisponible()<1) {
		optionalRevista.get().setEstado("AGOTADA");
		}
		
		
		reservaRepository.save(reserva);
        return "Reserva aprobada exitosamente.";
		
	}

	@Transactional
	public String rechazarReserva(Reserva reserva) {
		reserva.setEstado("RECHAZADA");
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
		return  this.reservaRepository.findReservasWithRevistaAndUsuarioByEstado("PENDIENTE");
	}
	
}

