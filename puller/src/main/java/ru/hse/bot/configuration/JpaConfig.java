package ru.hse.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.bot.domain.interfaces.DaoTgChatRepository;
import ru.hse.bot.domain.interfaces.DaoTrackRepository;
import ru.hse.bot.domain.interfaces.DaoWalletRepository;
import ru.hse.bot.domain.jpa.JpaTgChatRepository;
import ru.hse.bot.domain.jpa.JpaTrackRepository;
import ru.hse.bot.domain.jpa.JpaWalletRepository;
import ru.hse.bot.domain.repositories.TgChatRepository;
import ru.hse.bot.domain.repositories.TrackRepository;
import ru.hse.bot.domain.repositories.WalletRepository;

@Slf4j
@Configuration
public class JpaConfig {
    @Bean
    public DaoWalletRepository linkRepository(JpaWalletRepository jpaWalletRepository) {
        log.info("Creating JPA beans...");
        return new WalletRepository(jpaWalletRepository);
    }

    @Bean
    public DaoTgChatRepository tgChatRepository(JpaTgChatRepository jpaTgChatRepository) {
        return new TgChatRepository(jpaTgChatRepository);
    }

    @Bean
    public DaoTrackRepository trackRepository(JpaTrackRepository jpaTrackRepository) {
        return new TrackRepository(jpaTrackRepository);
    }
}
