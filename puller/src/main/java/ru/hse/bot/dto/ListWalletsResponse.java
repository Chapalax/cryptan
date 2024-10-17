package ru.hse.bot.dto;

import java.util.ArrayList;

public record ListWalletsResponse(ArrayList<WalletResponse> wallets, int size) {
}
