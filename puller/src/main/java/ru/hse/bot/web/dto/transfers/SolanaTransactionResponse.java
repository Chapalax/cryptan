package ru.hse.bot.web.dto.transfers;

import jakarta.validation.constraints.NotNull;

public record SolanaTransactionResponse(
        @NotNull String status,
        @NotNull String message,
        @NotNull SolanaTransactionListDataResponse result
) { }
