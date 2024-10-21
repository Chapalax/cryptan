package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record ListWalletsResponse(@NotNull ArrayList<WalletResponse> wallets, int size) {
}
