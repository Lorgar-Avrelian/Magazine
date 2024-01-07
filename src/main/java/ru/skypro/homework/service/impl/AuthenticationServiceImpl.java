package ru.skypro.homework.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList())
        );
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User newUser = userMapper.registerDtoToUser(registerDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            return userRepository.save(newUser);
        } else {
            return null;
        }
    }
}
