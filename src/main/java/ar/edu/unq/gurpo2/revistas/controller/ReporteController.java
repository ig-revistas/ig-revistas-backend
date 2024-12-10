package ar.edu.unq.gurpo2.revistas.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.gurpo2.revistas.dto.ReporteRevistaDTO;
import ar.edu.unq.gurpo2.revistas.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @GetMapping("/revistas")
    public List<ReporteRevistaDTO> obtenerReporteDeRevistas() {
        return reporteService.obtenerReporteDeRevistas();
    }
}
