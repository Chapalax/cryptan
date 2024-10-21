package ru.hse.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.hse.bot.configuration.ApplicationConfig;
import ru.hse.bot.configuration.WebClientConfig;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, WebClientConfig.class})
public class PullerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PullerApplication.class, args);
    }
}