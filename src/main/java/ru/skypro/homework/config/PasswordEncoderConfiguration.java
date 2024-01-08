package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;

@EnableWebSecurity
public class PasswordEncoderConfiguration {
    @Bean
    public Base64StringKeyGenerator passwordEncoder() {
        return new Base64StringKeyGenerator();
    }
}
