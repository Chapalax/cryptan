package ru.hse.bot.service.interfaces;

import org.jetbrains.annotations.NotNull;
import ru.hse.bot.domain.models.Wallet;

import java.util.HashMap;

public interface WalletService {
    Wallet add(long tgChatId, @NotNull String number, @NotNull String name);

    Wallet remove(long tgChatId, @NotNull String number);

    HashMap<String, String> listAll(long tgChatId);
}
