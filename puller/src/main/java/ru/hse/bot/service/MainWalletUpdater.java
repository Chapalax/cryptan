package ru.hse.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.hse.bot.domain.interfaces.DaoTrackRepository;
import ru.hse.bot.domain.interfaces.DaoWalletRepository;
import ru.hse.bot.domain.models.Track;
import ru.hse.bot.domain.models.Wallet;
import ru.hse.bot.dto.WalletUpdateResponse;
import ru.hse.bot.service.interfaces.MessageSender;
import ru.hse.bot.service.interfaces.WalletUpdater;
import ru.hse.bot.web.dto.SolanaDataResponse;
import ru.hse.bot.web.interfaces.WebClientSolana;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainWalletUpdater implements WalletUpdater {
    private final MessageSender sender;
    private final WebClientSolana solanaClient;
    private final DaoWalletRepository walletRepository;
    private final DaoTrackRepository trackRepository;

    @Override
    public void update() {
        List<Wallet> walletsToUpdate = walletRepository.findAllToUpdate();
        for (Wallet wallet : walletsToUpdate) {
            log.info("Founded wallet to update: {}", wallet.getNumber());
            wallet.setCheckedAt(Instant.now());
            SolanaDataResponse response = solanaClient.fetchWalletsTransactions(wallet.getNumber());
            if (wallet.getLastActivity().isBefore(Instant.ofEpochSecond(response.blockTime()))) {
                wallet.setLastActivity(Instant.ofEpochSecond(response.blockTime()));
                log.info("Wallet update successful, sending changes to bot...");
                sender.send(new WalletUpdateResponse(
                        wallet.getId(),
                        wallet.getNumber(),
                        response.signature(),
                        getUsers(wallet)
                ));
            }
            walletRepository.update(wallet);
        }
    }

    private @NotNull ArrayList<Long> getUsers(Wallet wallet) {
        List<Track> allTracks = trackRepository.findAllTracksWithWallet(wallet);
        ArrayList<Long> users = new ArrayList<>();
        for (Track track : allTracks) {
            users.add(track.getChatId());
        }
        return users;
    }
}
