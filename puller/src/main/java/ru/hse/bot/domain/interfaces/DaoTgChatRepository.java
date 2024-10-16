package ru.hse.bot.domain.interfaces;

import ru.hse.bot.domain.models.TgChat;

public interface DaoTgChatRepository extends Dao<TgChat> {
    Boolean isExists(TgChat chat);
}
