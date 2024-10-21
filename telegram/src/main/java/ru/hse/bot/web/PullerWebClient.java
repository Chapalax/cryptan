package ru.hse.bot.web;

import org.springframework.http.HttpStatus;
import ru.hse.bot.web.dto.AddWalletRequest;
import ru.hse.bot.web.dto.ListWalletsResponse;
import ru.hse.bot.web.dto.RemoveWalletRequest;
import ru.hse.bot.web.dto.WalletResponse;

public interface PullerWebClient {
    ListWalletsResponse getAllWallets(long tgChatId);

    WalletResponse addWallet(long tgChatId, AddWalletRequest addWalletRequest);

    WalletResponse deleteWallet(long tgChatId, RemoveWalletRequest removeWalletRequest);

    HttpStatus registerChat(long id);

    HttpStatus deleteChat(long id);
}
