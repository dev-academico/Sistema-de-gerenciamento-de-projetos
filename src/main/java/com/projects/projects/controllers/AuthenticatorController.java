package com.projects.projects.controllers;

import com.projects.projects.domain.user.User;
import com.projects.projects.domain.user.UserRole;
import com.projects.projects.domain.user.dto.AuthenticationDTO;
import com.projects.projects.domain.user.dto.LoginResponseDTO;
import com.projects.projects.domain.user.dto.RegisterDTO;
import com.projects.projects.infra.security.TokenService;
import com.projects.projects.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticatorController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private TokenService tokenService;
    @Autowired
    public AuthenticatorController(AuthenticationManager authenticationManager,  UserRepository userRepository,  TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        var authentication = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken(((User) authentication.getPrincipal()));

        System.out.println("Token: " + token);

        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO request) {
        if(this.userRepository.existsByLogin(request.getLogin())){
            return ResponseEntity.badRequest().build();
        };

        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        User user = new User(request.getLogin(), encodedPassword, request.getRole());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
