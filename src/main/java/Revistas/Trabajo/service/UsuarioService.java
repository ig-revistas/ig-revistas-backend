package Revistas.Trabajo.service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Revistas.Trabajo.model.Usuario;
import Revistas.Trabajo.model.Rol;
import Revistas.Trabajo.repository.UsuarioRepositoty;
import Revistas.Trabajo.repository.RolRepository;
import Revistas.Trabajo.security.UserInfoDetails;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositoty repository;

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

       Optional <Rol> rolOpt = rolRepository.findByNombre("USER_ROLE");
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
}

