package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.time.Clock;

/**
 * System clock configuration class. <br>
 * Applied to ensure the testability of the code. <br>
 * <br>
 * <hr>
 * <br>
 * Класс конфигурации системных часов. <br>
 * Применен для обеспечения тестируемости кода. <br>
 * <br>
 *
 * @see AdsServiceImpl
 */
@Configuration
public class ClockConfig {
    /**
     * Spring Bean, that returns {@link Clock#systemUTC()}, which is used to enter the current time. <br>
     * <br>
     * <hr>
     * <br>
     * Spring Bean, который возвращает {@link Clock#systemUTC()}, которые используются для ввода текущего времени. <br>
     * <br>
     * Создание этого Spring Bean - <p><b>шаг 6</b></p> настройки Spring Security 6.2.1. <br>
     * <br>
     *
     * @return Clock
     * @see Clock#systemUTC()
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
