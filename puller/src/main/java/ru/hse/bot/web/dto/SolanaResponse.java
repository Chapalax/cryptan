package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

public record SolanaResponse(@NotNull SolanaListDataResponse result) { }