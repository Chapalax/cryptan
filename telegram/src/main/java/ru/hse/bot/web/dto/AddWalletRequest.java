package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

public record AddWalletRequest(@NotNull String wallet, @NotNull String name) {
}
