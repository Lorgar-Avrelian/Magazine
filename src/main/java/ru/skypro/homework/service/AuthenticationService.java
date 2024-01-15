package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.config.SecurityFilterChainConfig;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.filter.BasicAuthCorsFilter;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;

import javax.transaction.Transactional;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Previous steps of Spring Security configuration are in the {@link BasicAuthCorsFilter}. <br>
 * Next steps of Spring Security configuration are in the {@link AuthenticationServiceImpl}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Предыдущие шаги настройки Spring Security находятся в классе {@link BasicAuthCorsFilter}. <br>
 * Следующие шаги настройки Spring Security находятся в классе {@link AuthenticationServiceImpl}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security authentication service. <br>
 * <br>
 * {@link AuthenticationService} is extending {@link UserDetailsService}. The {@link UserDetailsService} interface is used to retrieve user-related data. It has one method named {@link UserDetailsService#loadUserByUsername(String)} which can be overridden to customize the process of finding the user. <br>
 * It is used by the {@link SecurityFilterChainConfig#daoAuthenticationProvider()} to load details about the user during authentication. <br>
 * <br>
 * Creation of this class is the <p><b>9th step</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Сервис аутентификации Spring Security. <br>
 * <br>
 * {@link AuthenticationService} наследует {@link UserDetailsService}. {@link UserDetailsService} используется для получения данных, связанных с пользователем. Он имеет один метод с именем {@link UserDetailsService#loadUserByUsername(String)} который можно переопределить для настройки процесса поиска пользователя. <br>
 * Он используется {@link SecurityFilterChainConfig#daoAuthenticationProvider()} для загрузки сведений о пользователе во время аутентификации. <br>
 * <br>
 * Создание этого класса - <p><b>шаг 9</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see AuthenticationServiceImpl
 * @see UserDetailsService
 * @see BasicAuthCorsFilter
 */
public interface AuthenticationService extends UserDetailsService {
    /**
     * A method of the service for authentication users by login and password, that are contained in {@link LoginDTO}. <br>
     * <br>
     * Creation of this method is the <p><b>10th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для аутентификации пользователей по логину и паролю, которые содержатся в {@link LoginDTO}. <br>
     * <br>
     * Создание этого метода - <p><b>шаг 10</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param loginDTO
     * @return {@link User}
     * @see AuthenticationServiceImpl#login(LoginDTO)
     */
    @Transactional
    User login(LoginDTO loginDTO);

    /**
     * A method of the service for registration users  with username, password, firstname, lastname, phone and {@link Role}, that are contained in {@link RegisterDTO}. <br>
     * <br>
     * Creation of this method is the <p><b>11th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Метод сервиса для регистрации пользователей по имени пользователя, паролю, имени, фамилии, телефону и {@link Role}, которые содержатся в {@link RegisterDTO}. <br>
     * <br>
     * Создание этого метода - <p><b>шаг 11</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param registerDTO
     * @return {@link User}
     * @see AuthenticationServiceImpl#register(RegisterDTO)
     */
    User register(RegisterDTO registerDTO);
}
