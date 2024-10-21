package ru.hse.bot.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.hse.bot.schedule.Scheduler;

@Slf4j
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull Scheduler scheduler,
        @NotNull String queueName,
        @NotNull String exchangeName
) {
    @Bean
    public long schedulerIntervalMs(@NotNull ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
