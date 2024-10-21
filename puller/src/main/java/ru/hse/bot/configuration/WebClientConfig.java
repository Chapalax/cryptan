package ru.hse.bot.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.hse.bot.web.BotClient;
import ru.hse.bot.web.SolanaClient;
import ru.hse.bot.web.interfaces.WebClientBot;
import ru.hse.bot.web.interfaces.WebClientSolana;

@Validated
@ConfigurationProperties(prefix = "baseurl", ignoreUnknownFields = false)
public record WebClientConfig(String solana, String bot) {
    @Bean
    public @NotNull WebClientSolana solanaClient() {
        return new SolanaClient(solana);
    }

    @Bean
    public @NotNull WebClientBot botClient() {
        return new BotClient(bot);
    }
}
