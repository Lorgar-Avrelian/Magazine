package ru.skypro.homework.filter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skypro.homework.config.PasswordEncoderConfig;
import ru.skypro.homework.config.SecurityFilterChainConfig;
import ru.skypro.homework.service.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The beginning of Spring Security configuration is in the {@link SecurityFilterChainConfig}. <br>
 * Previous steps of Spring Security configuration are in the {@link PasswordEncoderConfig}. <br>
 * Next steps of Spring Security configuration are in the {@link AuthenticationService}. <br>
 * <br>
 * <hr>
 * <br>
 * Начало настройки Spring Security находится в классе {@link SecurityFilterChainConfig}. <br>
 * Предыдущие шаги настройки Spring Security находятся в классе {@link PasswordEncoderConfig}. <br>
 * Следующие шаги настройки Spring Security находятся в классе {@link AuthenticationService}. <br>
 * <br>
 * <hr>
 * <br>
 * <p>
 * Spring Security Cors Filter configuration class. <br>
 * <br>
 * {@link BasicAuthCorsFilter} is extending {@link OncePerRequestFilter}. Spring guarantees that the OncePerRequestFilter is executed only once for a given request. <br>
 * After creation of {@link BasicAuthCorsFilter} it must be injected into {@link SecurityFilterChainConfig#filterChain(HttpSecurity)}. <br>
 * <br>
 * Creation of this class is the <p><b>7th step (optional)</b></p> of configuration Spring Security 6.2.1. <br>
 * <br>
 * <hr>
 * <br>
 * Класс конфигурации Cors фильтра Spring Security. <br>
 * <br>
 * {@link BasicAuthCorsFilter} наследует {@link OncePerRequestFilter}. Spring гарантирует, что OncePerRequestFilter выполняется только один раз для данного запроса. <br>
 * После создания {@link BasicAuthCorsFilter} он должен быть введён в {@link SecurityFilterChainConfig#filterChain(HttpSecurity)}. <br>
 * <br>
 * Создание этого класса - <p><b>шаг 7 (не обязательный)</b></p> настройки Spring Security 6.2.1. <br>
 * <br>
 *
 * @see SecurityFilterChainConfig
 * @see PasswordEncoderConfig
 * @see AuthenticationService
 */
@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {
    /**
     * Method of {@link OncePerRequestFilter}. <br>
     * <br>
     * Creation of this Spring Bean is the <p><b>8th step (optional)</b></p> of configuration Spring Security 6.2.1. <br>
     * <br>
     * <hr>
     * <br>
     * Метод {@link OncePerRequestFilter}. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 8 (не обязательный)</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     * @see BasicAuthCorsFilter
     * @see SecurityFilterChainConfig
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
