package ar.edu.unq.gurpo2.revistas.service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unq.gurpo2.revistas.model.Reserva;
import ar.edu.unq.gurpo2.revistas.model.Rol;
import ar.edu.unq.gurpo2.revistas.model.Usuario;
import ar.edu.unq.gurpo2.revistas.repository.RolRepository;
import ar.edu.unq.gurpo2.revistas.repository.UsuarioRepositoy;
import ar.edu.unq.gurpo2.revistas.security.UserInfoDetails;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepositoy repository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> userDetail = repository.findByEmail(username);

		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	public String addUser(Usuario userInfo) {
		String idUsuario = UUID.randomUUID().toString();
		userInfo.setId(idUsuario);

		userInfo.setContrasenia(encoder.encode(userInfo.getContrasenia()));

		Optional<Rol> rolOpt = rolRepository.findByNombre("USER_ROLE");
		Rol rol = rolOpt.orElseGet(() -> {
			Rol newRol = new Rol();
			newRol.setId(UUID.randomUUID().toString());
			newRol.setNombre("USER_ROLE");
			rolRepository.save(newRol);
			return newRol;
		});

		userInfo.setRoles(List.of(rol));

		repository.save(userInfo);

		return "User Added Successfully";
	}

	public Usuario getUsuarioById(String id) {

		return this.repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado."));
	}

	


	public ArrayList<Usuario> getAllUsuariosOperador() {
		return this.repository
				   .findAll()
				   .stream()
				   .filter(u->u.getRoles()
				               .stream()
				               .anyMatch(r->r.getNombre().equals("OPERADOR_ROLE")))
				   .collect(Collectors.toCollection(ArrayList::new));
		
	}
	public void actualizarUsuario(Usuario usuario) {
        repository.save(usuario);
    }

    public void actualizarPortada(String usuarioId, String portadaUrl) {
        Usuario usuario = getUsuarioById(usuarioId);
        if (usuario != null) {
            usuario.setPortadaUrl(portadaUrl);
            actualizarUsuario(usuario);
        }
    }
	
    public Usuario obtenerUsuarioPorId(String usuarioId) {
    	
        return repository.findById(usuarioId).orElse(null);
    }
    public List<Reserva> obtenerReservasDeUsuario(String usuarioId) {
        Usuario usuario = repository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return usuario.getReservas(); 
    }

}