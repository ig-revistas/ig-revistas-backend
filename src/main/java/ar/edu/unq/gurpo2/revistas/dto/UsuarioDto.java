package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import ar.edu.unq.gurpo2.revistas.model.Usuario;

public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String password;
    private Set<String> roles; 

    public UsuarioDto() {
    }

    public UsuarioDto(Usuario user) {
        this.name = user.getNombre();
        this.email = user.getEmail();
        this.password = user.getContrasenia(); 
        this.roles = user.getRoles().stream()
        					.map(rol -> rol.getNombre())
        					.collect(Collectors.toSet());
        }

    public Set<String> getRoles() {  
        return roles;
    }

    public void setRoles(Set<String> roles) { 
        this.roles = roles;
    }

    public String getName() { 
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
