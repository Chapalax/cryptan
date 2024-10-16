package ru.hse.bot.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.bot.domain.interfaces.DaoTrackRepository;
import ru.hse.bot.domain.jpa.JpaTrackRepository;
import ru.hse.bot.domain.models.TgChat;
import ru.hse.bot.domain.models.Track;
import ru.hse.bot.domain.models.TrackPrimaryKey;
import ru.hse.bot.domain.models.Wallet;

import java.util.List;

@RequiredArgsConstructor
public class TrackRepository implements DaoTrackRepository {
    private final JpaTrackRepository trackRepository;

    @Override
    @Transactional
    public Boolean isTracked(TgChat chat, Wallet wallet) {
        return trackRepository.existsById(new TrackPrimaryKey(chat.getId(), wallet.getId()));
    }

    @Override
    @Transactional
    public Boolean isTrackedByAnyone(Wallet wallet) {
        return trackRepository.existsByWalletId(wallet.getId());
    }

    @Override
    @Transactional
    public List<Track> findAllTracksByUser(TgChat chat) {
        return trackRepository.findAllByChatId(chat.getId());
    }

    @Override
    @Transactional
    public List<Track> findAllTracksWithWallet(Wallet wallet) {
        return trackRepository.findAllByWalletId(wallet.getId());
    }

    @Override
    @Transactional
    public Track add(Track object) {
        return trackRepository.save(object);
    }

    @Override
    @Transactional
    public int remove(Track object) {
        if (trackRepository.existsById(new TrackPrimaryKey(object.getChatId(), object.getWalletId()))) {
            trackRepository.delete(object);
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    public void flush() {
        trackRepository.flush();
    }
}
