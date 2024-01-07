package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.User;

public interface AuthenticationService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    User register(RegisterDTO registerDTO);
}
