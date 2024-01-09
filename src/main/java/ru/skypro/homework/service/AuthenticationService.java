package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.User;

import javax.transaction.Transactional;

public interface AuthenticationService extends UserDetailsService {
    @Transactional
    User login(LoginDTO loginDTO);

    User register(RegisterDTO registerDTO);
}
