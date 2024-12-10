package ar.edu.unq.gurpo2.revistas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.gurpo2.revistas.dto.ReporteRevistaDTO;
import ar.edu.unq.gurpo2.revistas.model.EstadoReserva;
import ar.edu.unq.gurpo2.revistas.repository.ReservaRepository;

@Service
public class ReporteService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<ReporteRevistaDTO> obtenerReporteDeRevistas() {
        List<Object[]> resultados = reservaRepository.obtenerReservasYDevolucionesPorRevista(
            EstadoReserva.APROBADA, 
            EstadoReserva.DEVUELTA
        );

        return resultados.stream()
                .map(fila -> new ReporteRevistaDTO(
                        (String) fila[0], 
                        ((Number) fila[1]).intValue(), 
                        ((Number) fila[2]).intValue()))
                .toList();
    }
}
