package ru.hse.bot.web.interfaces;

import ru.hse.bot.web.dto.accounts.SolanaAccountDataResponse;
import ru.hse.bot.web.dto.tokens.SolanaTokenResponse;
import ru.hse.bot.web.dto.transfers.SolanaTransactionDataResponse;

import java.util.List;

public interface WebClientSolana {
    List<SolanaAccountDataResponse> fetchWalletsTransactions(String wallet, Long utcFrom);
    List<SolanaTransactionDataResponse> fetchTransactionInfo(String signature);
    SolanaTokenResponse fetchTokenInfo(String tokenHash);
}
