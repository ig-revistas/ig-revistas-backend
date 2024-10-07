package Revistas.Trabajo.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_info", schema = "revistas")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    private String nombre;
    private String email;
    private String contrasenia;

    @ElementCollection(fetch = FetchType.EAGER)  
    @CollectionTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "rol")
    private Set<String> roles;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPassword() { 
        return contrasenia;
    }

    public void setPassword(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
