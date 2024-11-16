package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import ar.edu.unq.gurpo2.revistas.model.Revista;

public class RevistaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String autor;
	private LocalDate fechaPublicacion;
	private String categoria;
	private String editorial;
	private String estado;
	private int cantidadDisponible;
	private String descripcion;

	public RevistaDto() {

	}

	public RevistaDto(Revista revista) {
		this.titulo = revista.getTitulo();
		this.autor = revista.getAutor();
		this.fechaPublicacion = revista.getFechaPublicacion();
		this.categoria = revista.getCategoria();
		this.editorial = revista.getEditorial();
		this.estado = revista.getEstado();
		this.cantidadDisponible = revista.getCantidadDisponible();
		this.descripcion = revista.getDescripcion();
	}

	public Revista toEntity() {
		Revista revista = new Revista();
		revista.setTitulo(this.titulo);
		revista.setAutor(this.autor);
		revista.setFechaPublicacion(this.fechaPublicacion);
		revista.setCategoria(this.categoria);
		revista.setEditorial(this.editorial);
		revista.setEstado(this.estado);
		revista.setCantidadDisponible(this.cantidadDisponible);
		revista.setDescripcion(this.descripcion);

		return revista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}