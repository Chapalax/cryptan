package ru.hse.bot.service.interfaces;

import org.jetbrains.annotations.NotNull;
import ru.hse.bot.domain.models.Wallet;

import java.util.List;

public interface WalletService {
    Wallet add(long tgChatId, @NotNull String number);

    Wallet remove(long tgChatId, @NotNull String number);

    List<Wallet> listAll(long tgChatId);
}
