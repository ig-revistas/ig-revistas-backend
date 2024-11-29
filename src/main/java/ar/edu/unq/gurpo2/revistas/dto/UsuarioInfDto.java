package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;

import ar.edu.unq.gurpo2.revistas.model.Usuario;

public class UsuarioInfDto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String id_usuario;
	private String email;
	
	public UsuarioInfDto(Usuario usuario) {
		this.id_usuario=usuario.getId();
		this.email=usuario.getEmail();
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
