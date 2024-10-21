package ru.hse.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.hse.bot.configuration.ApplicationConfig;
import ru.hse.bot.configuration.PullerClientConfig;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({ApplicationConfig.class, PullerClientConfig.class})
public class TelegramApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramApplication.class, args);
    }
}