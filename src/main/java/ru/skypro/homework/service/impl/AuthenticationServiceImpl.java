package ru.skypro.homework.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthenticationService;

import javax.transaction.Transactional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderConfig encoderConfiguration;

    public AuthenticationServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoderConfig encoderConfiguration) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoderConfiguration = encoderConfiguration;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public User login(LoginDTO loginDTO) {
        User loginUser = userMapper.loginDtoToUser(loginDTO);
        loginUser.setPassword(encoderConfiguration.passwordEncoder().encode(loginUser.getPassword()));
        User user;
        try {
            user = userRepository.findByEmail(loginUser.getEmail()).get();
        } catch (Exception e) {
            return null;
        }
        if (encoderConfiguration.passwordEncoder().matches(loginDTO.getPassword(), user.getPassword())) {
            loadUserByUsername(user.getEmail());
            return user;
        }
        return null;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User newUser = userMapper.registerDtoToUser(registerDTO);
        newUser.setPassword(encoderConfiguration.passwordEncoder().encode(newUser.getPassword()));
        if (userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            return userRepository.save(newUser);
        } else {
            return null;
        }
    }
}
