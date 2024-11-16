package ru.hse.bot.web.dto.accounts;

import jakarta.validation.constraints.NotNull;

public record SolanaAccountDataResponse(
        @NotNull Long blockTime,
        @NotNull String confirmationStatus,
        @NotNull String signature,
        @NotNull Long slot
) { }
