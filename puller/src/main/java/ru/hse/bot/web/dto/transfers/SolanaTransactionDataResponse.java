package ru.hse.bot.web.dto.transfers;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record SolanaTransactionDataResponse(
        @NotNull Integer instructionIndex,
        @NotNull Integer innerInstructionIndex,
        @NotNull String action,
        @NotNull String status,
        @NotNull String source,
        @Nullable String destination,
        @NotNull String token,
        @NotNull Long amount,
        @NotNull Long timestamp
) { }
