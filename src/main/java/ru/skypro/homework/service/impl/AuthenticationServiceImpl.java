package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
}
