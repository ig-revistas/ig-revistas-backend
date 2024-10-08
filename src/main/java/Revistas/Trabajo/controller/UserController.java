package Revistas.Trabajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Revistas.Trabajo.DTO.UserInfoDto;
import Revistas.Trabajo.model.UserInfo;
import Revistas.Trabajo.request.AuthRequest;
import Revistas.Trabajo.service.JwtService;
import Revistas.Trabajo.service.UserInfoService;



@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/Home")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/Registrarse")
    public ResponseEntity<?> registerUser(@RequestBody UserInfoDto userDto) {
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña no puede estar vacía ni ser null");
        }
        
       
        UserInfo userInfo = new UserInfo();
        userInfo.setNombre(userDto.getName());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setPassword(userDto.getPassword()); 
        userInfo.setRoles(userDto.getRoles());
        
        String responseMessage = service.addUser(userInfo); 
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}