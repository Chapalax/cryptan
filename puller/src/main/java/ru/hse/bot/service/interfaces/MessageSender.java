package ru.hse.bot.service.interfaces;

import ru.hse.bot.dto.WalletUpdateResponse;

public interface MessageSender {
    void send(WalletUpdateResponse update);
}
