package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skypro.homework.filter.BasicAuthCorsFilter;
import ru.skypro.homework.service.impl.AuthenticationServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Next steps of Spring Security configuration are in the {@link PasswordEncoderConfig}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Следующие шаги настройки Spring Security находятся в классе {@link PasswordEncoderConfig}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security main configuration class. <br>
 * <br>
 * Creation of this class is the <p><b>1st step</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Основной конфигурационный класс Spring Security. <br>
 * <br>
 * Создание этого класса - <p><b>шаг 1</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see PasswordEncoderConfig
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityFilterChainConfig {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordEncoderConfig encoderConfiguration;
    private final BasicAuthCorsFilter basicAuthCorsFilter;

    public SecurityFilterChainConfig(AuthenticationServiceImpl authenticationService, PasswordEncoderConfig encoderConfiguration, BasicAuthCorsFilter basicAuthCorsFilter) {
        this.authenticationService = authenticationService;
        this.encoderConfiguration = encoderConfiguration;
        this.basicAuthCorsFilter = basicAuthCorsFilter;
    }

    /**
     * Spring Bean, that activate {@link AuthenticationManager}. <br>
     * {@link AuthenticationManager} is using for authentication of users. <br>
     * <br>
     * Creation of this Spring Bean is the <p><b>2nd step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Spring Bean, который активирует {@link AuthenticationManager}. <br>
     * {@link AuthenticationManager} используется для аутентификации пользователей. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 2</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param authenticationConfiguration
     * @return {@link AuthenticationManager}
     * @throws Exception
     * @see AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Spring Bean, that activate {@link DaoAuthenticationProvider}. <br>
     * {@link DaoAuthenticationProvider} is the one of implementations of {@link AuthenticationProvider}, that is process authentication requests and returns fully authenticated object. <br>
     * DaoAuthenticationProvider retrieves the user details from a simple, read-only user DAO, the UserDetailsService. <br>
     * <br>
     * Creation of this Spring Bean is the <p><b>3rd step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Spring Bean, который активирует {@link DaoAuthenticationProvider}. <br>
     * {@link DaoAuthenticationProvider} является одной из реализаций {@link AuthenticationProvider}, который обрабатывает запросы авторизации и возвращает полностью аутентифицированный объект. <br>
     * DaoAuthenticationProvider получает данные пользователя из простого пользовательского DAO, доступного только для чтения, UserDetailsService. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 3</b></p> настройки Spring Security 6.2.1. <br>
     *
     * @return {@link DaoAuthenticationProvider}
     * @see DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoderConfiguration.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(authenticationService);
        return daoAuthenticationProvider;
    }

    /**
     * Spring Bean, that activate {@link SecurityFilterChain}. <br>
     * The configuration creates a Servlet Filter known as the {@link SecurityFilterChain}, which is responsible for all the security (protecting the application URLs, validating submitted username and passwords, redirecting to the log in form, and so on) within application. <br>
     * <br>
     * Creation of this Spring Bean is the <p><b>4th step</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Spring Bean, который активирует {@link SecurityFilterChain}. <br>
     * В конфигурации создается Servlet Filter, известный как {@link SecurityFilterChain}, который отвечает за всю безопасность (защиту URL-адресов приложения, проверку отправленных имени пользователя и паролей, перенаправление на форму входа и т.д.) в приложении. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 4</b></p> настройки Spring Security 6.2.1. <br>
     *
     * @param http
     * @return {@link SecurityFilterChain}
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests(
                    authorization ->
                            authorization
                                    .mvcMatchers("/", "/ads", "/login", "/register", "/swagger-resources/**", "/swagger-ui.html", "/v3/api-docs", "/webjars/**")
                                    .permitAll()
                                    .mvcMatchers("/ads/**", "/users/**")
                                    .authenticated())
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .cors()
            .and()
            .addFilterBefore(basicAuthCorsFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(withDefaults());
        return http.build();
    }
}