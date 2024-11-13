package ar.edu.unq.gurpo2.revistas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.unq.gurpo2.revistas.model.Reserva;
import ar.edu.unq.gurpo2.revistas.repository.ReservaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {

	@Autowired
	public ReservaRepository reservaRepository;

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
	public boolean existeReserva(String idUsuario, Integer idRevista) {
	    return reservaRepository.existsByUsuarioIdAndRevistaId(idUsuario, idRevista);
	}
	public java.util.Optional<Reserva> getReservaFindById(String idReserva) {
		return reservaRepository.findById(idReserva);
	}
	public String aprobarReserva(Reserva reserva) {
		reserva.setEstado("APROBADA");
		reserva.setFechaAprobacion(LocalDate.now());
		reservaRepository.save(reserva);
		
        return "Reserva aprobada exitosamente.";
		
	}
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

	
}


