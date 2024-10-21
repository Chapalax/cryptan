package ru.hse.bot.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.bot.domain.models.Track;
import ru.hse.bot.domain.models.TrackPrimaryKey;

import java.util.List;

@Repository
public interface JpaTrackRepository extends JpaRepository<Track, TrackPrimaryKey> {
    Boolean existsByWalletId(Long walletId);

    List<Track> findAllByChatId(Long chatId);

    List<Track> findAllByWalletId(Long walletId);
}
