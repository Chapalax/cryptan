package ru.hse.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.bot.domain.interfaces.DaoTgChatRepository;
import ru.hse.bot.domain.models.TgChat;
import ru.hse.bot.exceptions.ChatNotFoundException;
import ru.hse.bot.exceptions.RegisteredUserExistsException;
import ru.hse.bot.service.interfaces.TgChatService;

@Service
@RequiredArgsConstructor
public class MainTgChatService implements TgChatService {
    private final DaoTgChatRepository tgChatRepository;

    @Override
    public void register(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if (tgChatRepository.isExists(chat)) {
            throw new RegisteredUserExistsException("You're already registered");
        }
        tgChatRepository.add(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if (tgChatRepository.remove(chat) == 0) {
            throw new ChatNotFoundException("Chat not found.");
        }
    }
}
