package com.projects.projects.controllers;

import com.projects.projects.domain.user.User;
import com.projects.projects.domain.user.UserRole;
import com.projects.projects.domain.user.dto.AuthenticationDTO;
import com.projects.projects.domain.user.dto.RegisterDTO;
import com.projects.projects.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/auth")
public class AuthenticatorController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    @Autowired
    public AuthenticatorController(AuthenticationManager authenticationManager,  UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        var authentication = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
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
