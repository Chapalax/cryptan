package ru.hse.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.hse.bot.web.PullerClient;
import ru.hse.bot.web.PullerWebClient;

@Validated
@ConfigurationProperties(prefix = "baseurl", ignoreUnknownFields = false)
public record PullerClientConfig(@NotNull String puller) {
    @Bean
    public @NotNull PullerWebClient pullerWebClient() {
        return new PullerClient(puller);
    }
}
