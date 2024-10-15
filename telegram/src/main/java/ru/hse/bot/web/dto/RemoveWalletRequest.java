package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveWalletRequest(@NotNull String wallet) {
}
