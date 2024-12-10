package ar.edu.unq.gurpo2.revistas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unq.gurpo2.revistas.dto.ReporteDto;


@Service
public class SistemaService {
	
	@Autowired
    private ReservaService reservaService;
	@Autowired
    private RevistaService revistaService;
	
    public ReporteDto getReporteSistema() {
    
    	
        ReporteDto reporte = new ReporteDto();
        reporte.setRevistasTotales(this.revistaService.getCantidadDeRevistas());
        reporte.setReservasTotales(this.reservaService.getCantidadDeReservas());
        
        return reporte;
        
    }
}