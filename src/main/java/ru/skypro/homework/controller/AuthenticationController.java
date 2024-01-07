package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.JwtToken;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;
import ru.skypro.homework.utils.JwtTokenUtils;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationServiceImpl authenticationService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
        UserDetails userDetails;
        try {
            userDetails = authenticationService.loadUserByUsername(loginDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtToken(token));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        User user = authenticationService.register(registerDTO);
        if (user != null) {
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
