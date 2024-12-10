package ar.edu.unq.gurpo2.revistas.dto;



public class ReporteDto {

	
	private long revistasTotales;
	private long reservasTotales;
	public ReporteDto() {}
	
	public ReporteDto(long revistasTotales,long reservasTotale) {
		this.reservasTotales=reservasTotale;
		this.revistasTotales=revistasTotales;
	}

	public long getRevistasTotales() {
		return revistasTotales;
	}

	public void setRevistasTotales(long revistasTotales) {
		this.revistasTotales = revistasTotales;
	}

	public long getReservasTotales() {
		return reservasTotales;
	}

	public void setReservasTotales(long reservasTotale) {
		this.reservasTotales = reservasTotale;
	}
	
	
	
}
