package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SolanaListDataResponse(
        @NotNull List<SolanaDataResponse> data,
        @NotNull SolanaPaginationResponse pagination
) { }
