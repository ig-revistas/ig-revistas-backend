package Revistas.Trabajo.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import Revistas.Trabajo.model.UserInfo;

public class UserInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
    private String email;
    private String password;
    private List<String> roles;
    
    public UserInfoDto(UserInfo user) {
		this.name=user.getName();
		this.email=user.getEmail();
		this.password=user.getPassword();
		this.roles=user.getRoles()
					   .stream()
					   .map(rol->rol.name())  
	                   .collect(Collectors.toList());
	}
    
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
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
