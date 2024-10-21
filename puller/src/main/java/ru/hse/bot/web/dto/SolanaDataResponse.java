package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

public record SolanaDataResponse(
        @NotNull Long blockTime,
        @NotNull String confirmationStatus,
        @NotNull String signature,
        @NotNull Long slot
) { }
