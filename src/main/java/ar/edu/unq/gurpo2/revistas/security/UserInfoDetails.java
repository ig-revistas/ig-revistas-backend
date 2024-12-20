package ar.edu.unq.gurpo2.revistas.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.edu.unq.gurpo2.revistas.model.Usuario;

public class UserInfoDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserInfoDetails(Usuario userInfo) {
		this.usuario = userInfo;
		this.username = userInfo.getEmail();
		this.password = userInfo.getContrasenia();
		this.authorities = userInfo.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.collect(Collectors.toList());
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}