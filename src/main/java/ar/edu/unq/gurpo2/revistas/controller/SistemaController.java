package ar.edu.unq.gurpo2.revistas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.gurpo2.revistas.dto.ReporteDto;
import ar.edu.unq.gurpo2.revistas.service.SistemaService;

@RestController
@RequestMapping("/sistema")
public class SistemaController {

	@Autowired
	private SistemaService sistemaService;

//	@Autowired
//	private LamadridService lamadridService;
	
	@PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @GetMapping("/reporte")
    private ResponseEntity<?> reporteTotales() {
		
		try {
    	 
    	 ReporteDto reporte= this.sistemaService.getReporteSistema();
    	 
	 	return ResponseEntity.status(HttpStatus.OK).body(reporte);
//	 	return ResponseEntity.status(HttpStatus.OK).body("LAMADRID");
		}catch (Exception e) {
			e.printStackTrace();
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte: " + e.getMessage());
		}
    	 
	}
}
