package ru.hse.bot.service.interfaces;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);
}
