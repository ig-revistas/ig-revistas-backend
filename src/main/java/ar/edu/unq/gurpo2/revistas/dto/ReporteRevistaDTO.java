package ar.edu.unq.gurpo2.revistas.dto;

public class ReporteRevistaDTO {
    private String titulo;
    private int cantidadReservadas;
    private int cantidadDevueltas;

    public ReporteRevistaDTO(String titulo, int cantidadReservadas, int cantidadDevueltas) {
        this.setTitulo(titulo);
        this.setCantidadReservadas(cantidadReservadas);
        this.setCantidadDevueltas(cantidadDevueltas);
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getCantidadReservadas() {
		return cantidadReservadas;
	}

	public void setCantidadReservadas(int cantidadReservadas) {
		this.cantidadReservadas = cantidadReservadas;
	}

	public int getCantidadDevueltas() {
		return cantidadDevueltas;
	}

	public void setCantidadDevueltas(int cantidadDevueltas) {
		this.cantidadDevueltas = cantidadDevueltas;
	}

}
