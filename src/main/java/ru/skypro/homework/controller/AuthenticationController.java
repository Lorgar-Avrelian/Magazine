package ru.skypro.homework.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = authenticationService.login(loginDTO);
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(user.getEmail(), user.getPassword());
            return ResponseEntity.ok().headers(headers).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        User user = authenticationService.register(registerDTO);
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(user.getEmail(), user.getPassword());
            return ResponseEntity.status(201).headers(headers).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
