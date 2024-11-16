package ru.hse.bot.web.dto.accounts;

import jakarta.validation.constraints.NotNull;

public record SolanaAccountResponse(@NotNull SolanaAccountListDataResponse result) { }
