package ru.hse.bot.service;

import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.hse.bot.domain.interfaces.DaoTgChatRepository;
import ru.hse.bot.domain.interfaces.DaoTrackRepository;
import ru.hse.bot.domain.interfaces.DaoWalletRepository;
import ru.hse.bot.domain.models.TgChat;
import ru.hse.bot.domain.models.Track;
import ru.hse.bot.domain.models.Wallet;
import ru.hse.bot.exceptions.AddedWalletExistsException;
import ru.hse.bot.exceptions.ChatNotFoundException;
import ru.hse.bot.exceptions.WalletNotFoundException;
import ru.hse.bot.service.interfaces.WalletService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainWalletService implements WalletService {
    private final DaoWalletRepository walletRepository;
    private final DaoTgChatRepository tgChatRepository;
    private final DaoTrackRepository trackRepository;
    private final Gauge activeUsersGauge = Gauge.build()
            .name("bot_users_active")
            .help("Total users with minimum 1 tracked wallet")
            .register();
    private final Gauge walletsGauge = Gauge.build()
            .name("puller_wallets")
            .help("Total tracked wallets by system")
            .register();

    @Override
    public Wallet add(long tgChatId, @NotNull String number, @NotNull String name) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);

        if (!tgChatRepository.isExists(chat)) {
            throw new ChatNotFoundException("Chat not found.");
        }

        Wallet wallet = new Wallet();
        wallet.setNumber(number);

        if (walletRepository.isExists(wallet)) {
            wallet = walletRepository.findByNumber(wallet);
            if (trackRepository.isTracked(chat, wallet)) {
                throw new AddedWalletExistsException("Wallet already added.");
            }
        } else {
            wallet = walletRepository.add(wallet);
            walletsGauge.inc();
        }

        if (trackRepository.findAllTracksByUser(chat).isEmpty()) {
            activeUsersGauge.inc();
        }
        Track track = new Track();
        track.setChatId(tgChatId);
        track.setWalletId(wallet.getId());
        track.setWalletName(name);
        trackRepository.add(track);
        return wallet;
    }

    @Override
    public Wallet remove(long tgChatId, @NotNull String number) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        if (!tgChatRepository.isExists(chat)) {
            throw new ChatNotFoundException("Chat not found.");
        }
        Wallet wallet = new Wallet();
        wallet.setNumber(number);

        if (!walletRepository.isExists(wallet)) {
            throw new WalletNotFoundException("Link not found.");
        }
        wallet = walletRepository.findByNumber(wallet);
        Track track = new Track();
        track.setChatId(tgChatId);
        track.setWalletId(wallet.getId());
        if (trackRepository.remove(track) == 0) {
            throw new WalletNotFoundException("You are not following this link.");
        }
        if (!trackRepository.isTrackedByAnyone(wallet)) {
            walletRepository.remove(wallet);
            walletsGauge.dec();
        }
        if (trackRepository.findAllTracksByUser(chat).isEmpty()) {
            activeUsersGauge.dec();
        }
        return wallet;
    }

    @Override
    public List<Wallet> listAll(long tgChatId) {
        TgChat chat = new TgChat();
        chat.setId(tgChatId);
        List<Wallet> wallets = new ArrayList<>();

        if (!tgChatRepository.isExists(chat)) {
            throw new ChatNotFoundException("Chat not found.");
        }
        List<Track> allTracks = trackRepository.findAllTracksByUser(chat);
        for (Track current : allTracks) {
            Wallet temp = new Wallet();
            temp.setId(current.getWalletId());
            wallets.add(walletRepository.findById(temp));
        }
        return wallets;
    }
}
