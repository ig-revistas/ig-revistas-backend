package ar.edu.unq.gurpo2.revistas.service;

import org.springframework.stereotype.Service;
import ar.edu.unq.gurpo2.revistas.dto.ReporteDto;

@Service
public class SistemaService {
	
//	@Autowired
//    private ReservaService reservaService;
//	@Autowired
//    private RevistaService revistaService;
	
    public ReporteDto getReporteSistema() {
    	System.out.println("HOLAAAAAA !!!!!!");
        ReporteDto reporte = new ReporteDto();
        reporte.setRevistasTotales(50);//this.revistaService.getCantidadDeRevistas();
        reporte.setReservasTotales(40);//this.reservaService.getCantidadDeReservas();
        return reporte;
    }
}