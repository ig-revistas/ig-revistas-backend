package ar.edu.unq.gurpo2.revistas.dto;

public class UserAuthDto {

	private String token;
	private UsuarioDto usuario;

	public UserAuthDto(String token, UsuarioDto usuario) {
		this.token = token;
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
}
