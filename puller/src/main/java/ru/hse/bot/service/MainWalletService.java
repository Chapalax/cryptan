package ru.hse.bot.service;

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
import ru.hse.bot.web.dto.SolanaDataResponse;
import ru.hse.bot.web.interfaces.WebClientSolana;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainWalletService implements WalletService {
    private final DaoWalletRepository walletRepository;
    private final DaoTgChatRepository tgChatRepository;
    private final DaoTrackRepository trackRepository;
    private final WebClientSolana solanaClient;

    @Override
    public Wallet add(long tgChatId, @NotNull String number) {
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
            SolanaDataResponse response = solanaClient.fetchWalletsTransactions(wallet.getNumber());
            wallet.setLastActivity(Instant.ofEpochSecond(response.blockTime()));
            wallet = walletRepository.add(wallet);
        }

        Track track = new Track();
        track.setChatId(tgChatId);
        track.setWalletId(wallet.getId());
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
