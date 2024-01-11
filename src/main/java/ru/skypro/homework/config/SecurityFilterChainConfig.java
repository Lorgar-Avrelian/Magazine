package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityFilterChainConfig {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordEncoderConfiguration encoderConfiguration;
    private final BasicAuthCorsFilter basicAuthCorsFilter;

    public SecurityFilterChainConfig(AuthenticationServiceImpl authenticationService, PasswordEncoderConfiguration encoderConfiguration, BasicAuthCorsFilter basicAuthCorsFilter) {
        this.authenticationService = authenticationService;
        this.encoderConfiguration = encoderConfiguration;
        this.basicAuthCorsFilter = basicAuthCorsFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoderConfiguration.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(authenticationService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests(
                    authorization ->
                            authorization
                                    .mvcMatchers("/ads", "/login", "/register", "/swagger-resources/**", "/swagger-ui.html", "/v3/api-docs", "/webjars/**")
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