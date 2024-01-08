package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationServiceImpl authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = authenticationService.login(loginDTO);
        return ResponseEntity.ok().build();
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
