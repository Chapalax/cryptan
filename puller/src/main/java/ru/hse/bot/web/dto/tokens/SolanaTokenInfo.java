package ru.hse.bot.web.dto.tokens;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record SolanaTokenInfo(
        @NotNull String name,
        @NotNull String symbol,
        @URL @NotNull String image
) { }
