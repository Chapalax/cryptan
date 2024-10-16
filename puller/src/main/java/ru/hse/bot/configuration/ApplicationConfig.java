package ru.hse.bot.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.hse.bot.domain.jpa.JpaTgChatRepository;
import ru.hse.bot.domain.jpa.JpaTrackRepository;
import ru.hse.bot.domain.jpa.JpaWalletRepository;
import ru.hse.bot.domain.repositories.TgChatRepository;
import ru.hse.bot.domain.repositories.TrackRepository;
import ru.hse.bot.domain.repositories.WalletRepository;
import ru.hse.bot.schedule.Scheduler;

@Slf4j
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull Scheduler scheduler,
        @NotNull Boolean useQueue,
        @NotNull String queueName,
        @NotNull String exchangeName
) {
    @Bean
    public long schedulerIntervalMs(@NotNull ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    public WalletRepository linkRepository(JpaWalletRepository jpaWalletRepository) {
        log.info("Creating JPA beans...");
        return new WalletRepository(jpaWalletRepository);
    }

    @Bean
    public TgChatRepository tgChatRepository(JpaTgChatRepository jpaTgChatRepository) {
        return new TgChatRepository(jpaTgChatRepository);
    }

    @Bean
    public TrackRepository trackRepository(JpaTrackRepository jpaTrackRepository) {
        return new TrackRepository(jpaTrackRepository);
    }
}
