package ru.hse.bot.domain.interfaces;

import ru.hse.bot.domain.models.TgChat;
import ru.hse.bot.domain.models.Track;
import ru.hse.bot.domain.models.Wallet;

import java.util.List;

public interface DaoTrackRepository extends Dao<Track> {
    Boolean isTracked(TgChat chat, Wallet wallet);

    Boolean isTrackedByAnyone(Wallet wallet);

    List<Track> findAllTracksByUser(TgChat chat);

    List<Track> findAllTracksWithWallet(Wallet wallet);
}
