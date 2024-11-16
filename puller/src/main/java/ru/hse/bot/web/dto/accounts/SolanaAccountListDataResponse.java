package ru.hse.bot.web.dto.accounts;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SolanaAccountListDataResponse(
        @NotNull List<SolanaAccountDataResponse> data,
        @NotNull SolanaPaginationResponse pagination
) { }
