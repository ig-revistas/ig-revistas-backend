package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;

import ar.edu.unq.gurpo2.revistas.model.Revista;

public class RevistaInfDto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String id_revista;
	private String titulo;
	
	public RevistaInfDto(Revista revista ) {
		this.id_revista=revista.getId();
		this.titulo=revista.getTitulo();
	}

	public String getId_revista() {
		return id_revista;
	}

	public void setId_revista(String id_revista) {
		this.id_revista = id_revista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	

}
