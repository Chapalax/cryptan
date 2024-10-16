package ru.hse.bot.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.bot.domain.interfaces.DaoTgChatRepository;
import ru.hse.bot.domain.jpa.JpaTgChatRepository;
import ru.hse.bot.domain.models.TgChat;

import java.util.List;

@RequiredArgsConstructor
public class TgChatRepository implements DaoTgChatRepository {
    private final JpaTgChatRepository tgChatRepository;

    @Override
    @Transactional
    public Boolean isExists(TgChat chat) {
        return tgChatRepository.existsById(chat.getId());
    }

    @Override
    @Transactional
    public TgChat add(TgChat object) {
        return tgChatRepository.save(object);
    }

    @Override
    @Transactional
    public int remove(TgChat object) {
        if (tgChatRepository.existsById(object.getId())) {
            tgChatRepository.delete(object);
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<TgChat> findAll() {
        return tgChatRepository.findAll();
    }

    public void flush() {
        tgChatRepository.flush();
    }
}
