package ru.hse.bot.web.interfaces;

import org.springframework.http.HttpStatus;
import ru.hse.bot.dto.WalletUpdateResponse;

public interface WebClientBot {
    HttpStatus sendUpdates(WalletUpdateResponse walletUpdate);
}
