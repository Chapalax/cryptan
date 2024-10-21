package ru.hse.bot.web.interfaces;

import ru.hse.bot.web.dto.SolanaDataResponse;

public interface WebClientSolana {
    SolanaDataResponse fetchWalletsTransactions(String wallet);
}
