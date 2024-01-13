package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.skypro.homework.service.UsersService;

/**
 * Spring Security PasswordEncoder configuration class. <br>
 * Used for exclusion circular dependency between {@link SecurityFilterChainConfig} and injected implementation of {@link UsersService} <br>
 * <br>
 * <hr>
 * <br>
 * Spring Security класс конфигурации PasswordEncoder-а. <br>
 * Использован для исключения циклической зависимости между {@link SecurityFilterChainConfig} и введённой реализацией {@link UsersService} <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see UsersService
 */
@EnableWebSecurity
public class PasswordEncoderConfig {
    /**
     * Bean, that returns {@link BCryptPasswordEncoder}, which is used for encode/decode passwords. <br>
     * <br>
     * <hr>
     * <br>
     * Бин, который возвращает {@link BCryptPasswordEncoder}, использующийся для кодирования/декодирования паролей. <br>
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