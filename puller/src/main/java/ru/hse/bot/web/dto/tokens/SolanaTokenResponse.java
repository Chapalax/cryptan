package ru.hse.bot.web.dto.tokens;

import jakarta.validation.constraints.NotNull;

public record SolanaTokenResponse(
        @NotNull String tokenType,
        @NotNull SolanaTokenInfo tokenList
) { }
