package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.skypro.homework.filter.BasicAuthCorsFilter;
import ru.skypro.homework.service.UsersService;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Previous steps of Spring Security configuration are in the {@link SecurityFilterChainConfig}. <br>
 * Next steps of Spring Security configuration are in the {@link BasicAuthCorsFilter}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Предыдущие шаги настройки Spring Security находятся в классе {@link SecurityFilterChainConfig}. <br>
 * Следующие шаги настройки Spring Security находятся в классе {@link BasicAuthCorsFilter}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security PasswordEncoder configuration class. <br>
 * Used for exclusion circular dependency between {@link SecurityFilterChainConfig} and injected implementation of {@link UsersService} <br>
 * <br>
 * Creation of this class is the <p><b>5th step</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Spring Security класс конфигурации PasswordEncoder-а. <br>
 * Использован для исключения циклической зависимости между {@link SecurityFilterChainConfig} и введённой реализацией {@link UsersService} <br>
 * <br>
 * Создание этого класса - <p><b>шаг 5</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see UsersService
 * @see BasicAuthCorsFilter
 */
@EnableWebSecurity
public class PasswordEncoderConfig {
    /**
     * Spring Bean, that returns {@link BCryptPasswordEncoder}, which is used for encode/decode passwords. <br>
     * <br>
     * Creation of this Spring Bean is the <p><b>6th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Spring Bean, который возвращает {@link BCryptPasswordEncoder}, использующийся для кодирования/декодирования паролей. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 6</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @return BCryptPasswordEncoder
     * @see BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}