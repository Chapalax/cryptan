package ru.hse.bot.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.bot.domain.models.TgChat;

@Repository
public interface JpaTgChatRepository extends JpaRepository<TgChat, Long> {
}
