package ru.hse.bot.web.dto.transfers;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SolanaTransactionListDataResponse(
        @NotNull String transactionHash,
        @NotNull List<SolanaTransactionDataResponse> data
) { }
