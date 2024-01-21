package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.config.SecurityFilterChainConfig;
import ru.skypro.homework.controller.AuthenticationController;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthenticationService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Previous steps of Spring Security configuration are in the {@link AuthenticationService}. <br>
 * Next steps of Spring Security configuration are in the {@link AuthenticationController}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Предыдущие шаги настройки Spring Security находятся в классе {@link AuthenticationService}. <br>
 * Следующие шаги настройки Spring Security находятся в классе {@link AuthenticationController}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security authentication service implementation. <br>
 * <br>
 * {@link AuthenticationServiceImpl} is extending {@link AuthenticationService} and {@link UserDetailsService}. The {@link UserDetailsService} interface is used to retrieve user-related data. It has one method named {@link UserDetailsService#loadUserByUsername(String)} which can be overridden to customize the process of finding the user. <br>
 * It is used by the {@link SecurityFilterChainConfig#daoAuthenticationProvider()} to load details about the user during authentication. <br>
 * <br>
 * Creation of this class is the <p><b>12th step</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Реализация сервиса аутентификации Spring Security. <br>
 * <br>
 * {@link AuthenticationServiceImpl} наследует {@link AuthenticationService} и {@link UserDetailsService}. {@link UserDetailsService} используется для получения данных, связанных с пользователем. Он имеет один метод с именем {@link UserDetailsService#loadUserByUsername(String)} который можно переопределить для настройки процесса поиска пользователя. <br>
 * Он используется {@link SecurityFilterChainConfig#daoAuthenticationProvider()} для загрузки сведений о пользователе во время аутентификации. <br>
 * <br>
 * Создание этого класса - <p><b>шаг 12</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see UserDetailsService
 * @see AuthenticationService
 * @see AuthenticationController
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderConfig encoderConfiguration;
    private static final Logger log = Logger.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoderConfig encoderConfiguration) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoderConfiguration = encoderConfiguration;
    }

    /**
     * This method retrieve the {@link User} object using the DAO, and if it exists, wrap it into a {@link Principal} object, which implements {@link UserDetails}, and returns it. <br>
     * <br>
     * Used {@link JpaRepository} method {@link UserRepository#findByEmail(String)}. <br>
     * After creation of {@link AuthenticationServiceImpl#loadUserByUsername(String)} it must be injected into {@link SecurityFilterChainConfig#daoAuthenticationProvider()}. <br>
     * <br>
     * Creation of this method is the <p><b>13th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Этот метод извлекает объект {@link User}, используя DAO, и, если он существует, оборачивает его в объект {@link Principal}, который реализует {@link UserDetails}, и возвращает его. <br>
     * После создания {@link AuthenticationServiceImpl#loadUserByUsername(String)} он должен быть введён в {@link SecurityFilterChainConfig#daoAuthenticationProvider()}. <br>
     * <br>
     * Использован {@link JpaRepository} метод {@link UserRepository#findByEmail(String)}. <br>
     * <br>
     * Создание этого метода - <p><b>шаг 13</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param username
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException
     * @see Principal
     * @see UserDetails
     * @see JpaRepository
     * @see UserRepository#findByEmail(String)
     * @see SecurityFilterChainConfig
     * @see SecurityFilterChainConfig#daoAuthenticationProvider()
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByEmail(username).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Stream.of(user.getRole()).map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * A method of the service implementation for authentication users by login and password, that are contained in {@link LoginDTO}. <br>
     * <br>
     * Used {@link JpaRepository} method {@link UserRepository#findByEmail(String)}, {@link Mapper} method {@link UserMapper#loginDtoToUser(LoginDTO)} and {@link PasswordEncoder} methods {@link BCryptPasswordEncoder#encode(CharSequence)} and {@link BCryptPasswordEncoder#matches(CharSequence, String)}. <br>
     * <br>
     * Creation of this method is the <p><b>14th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Метод реализации сервиса для аутентификации пользователей по логину и паролю, которые содержатся в {@link LoginDTO}. <br>
     * <br>
     * Использованы {@link JpaRepository} метод {@link UserRepository#findByEmail(String)}, {@link Mapper} метод {@link UserMapper#loginDtoToUser(LoginDTO)} и {@link PasswordEncoder} методы {@link BCryptPasswordEncoder#encode(CharSequence)} и {@link BCryptPasswordEncoder#matches(CharSequence, String)}. <br>
     * <br>
     * Создание этого метода - <p><b>шаг 14</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param loginDTO
     * @return {@link User}
     * @see JpaRepository
     * @see UserRepository#findByEmail(String)
     * @see Mapper
     * @see UserMapper#loginDtoToUser(LoginDTO)
     * @see PasswordEncoder
     * @see BCryptPasswordEncoder#encode(CharSequence)
     * @see BCryptPasswordEncoder#matches(CharSequence, String)
     * @see SecurityFilterChainConfig
     */
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

    /**
     * A method of the service implementation for registration users  with username, password, firstname, lastname, phone and {@link Role}, that are contained in {@link RegisterDTO}. <br>
     * <br>
     * Used {@link JpaRepository} methods {@link UserRepository#findByEmail(String)} and {@link UserRepository#save(Object)}, {@link Mapper} method {@link UserMapper#registerDtoToUser(RegisterDTO)} and {@link PasswordEncoder} method {@link BCryptPasswordEncoder#encode(CharSequence)}. <br>
     * <br>
     * Creation of this method is the <p><b>15th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Метод реализации сервиса для регистрации пользователей по имени пользователя, паролю, имени, фамилии, телефону и {@link Role}, которые содержатся в {@link RegisterDTO}. <br>
     * <br>
     * Использованы {@link JpaRepository} методы {@link UserRepository#findByEmail(String)} и {@link UserRepository#save(Object)}, {@link Mapper} метод {@link UserMapper#registerDtoToUser(RegisterDTO)} и {@link PasswordEncoder} метод {@link BCryptPasswordEncoder#encode(CharSequence)}. <br>
     * <br>
     * Создание этого метода - <p><b>шаг 15</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param registerDTO
     * @return {@link User}
     * @see JpaRepository
     * @see UserRepository#findByEmail(String)
     * @see UserRepository#save(Object)
     * @see Mapper
     * @see UserMapper#registerDtoToUser(RegisterDTO)
     * @see PasswordEncoder
     * @see BCryptPasswordEncoder#encode(CharSequence)
     * @see SecurityFilterChainConfig
     */
    @Override
    public User register(RegisterDTO registerDTO) {
        User newUser = userMapper.registerDtoToUser(registerDTO);
        newUser.setPassword(encoderConfiguration.passwordEncoder().encode(newUser.getPassword()));
        User registredUser = null;
        try {
            registredUser = userRepository.findByEmail(newUser.getEmail()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (registredUser == null) {
            return userRepository.save(newUser);
        } else {
            return null;
        }
    }
}
