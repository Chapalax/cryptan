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
import ru.hse.bot.utility.TimeConverter;
import ru.hse.bot.web.dto.accounts.SolanaAccountDataResponse;
import ru.hse.bot.web.dto.tokens.SolanaTokenResponse;
import ru.hse.bot.web.dto.transfers.SolanaTransactionDataResponse;
import ru.hse.bot.web.interfaces.WebClientSolana;

import java.time.OffsetDateTime;
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
            wallet.setCheckedAt(OffsetDateTime.now());
            List<SolanaAccountDataResponse> response = solanaClient.fetchWalletsTransactions(
                    wallet.getNumber(),
                    wallet.getLastActivity().toEpochSecond()
            );

            for (SolanaAccountDataResponse data : response) {
                if (wallet.getLastActivity().isBefore(convertTimeFromUtc(data.blockTime()))) {
                    wallet.setLastActivity(convertTimeFromUtc(data.blockTime()));
                }
                List<SolanaTransactionDataResponse> transferList = solanaClient.fetchTransactionInfo(data.signature());
                String sourceTokenKey = "";
                String destinationTokenKey = "";
                long sourceTokenAmount = 0;
                long destinationTokenAmount = 0;

                for (SolanaTransactionDataResponse transfer : transferList) {
                    if (transfer.action().equals("transfer") &&
                            !transfer.token().isEmpty() &&
                            transfer.status().equals("Successful") &&
                            transfer.destination() != null
                    ) {
                        if (transfer.source().equals(wallet.getNumber()) && sourceTokenKey.isEmpty()) {
                            sourceTokenKey = transfer.token();
                            sourceTokenAmount = transfer.amount();
                            log.info("Source Token is {}, {}", sourceTokenKey, sourceTokenAmount);
                        }
                        if (transfer.destination().equals(wallet.getNumber())) {
                            destinationTokenKey = transfer.token();
                            destinationTokenAmount = transfer.amount();
                            log.info("Destination Token is {}, {}", destinationTokenKey, destinationTokenAmount);
                        }
                    }
                }

                if (!sourceTokenKey.isEmpty() && !destinationTokenKey.isEmpty()) {
                    try {
                        SolanaTokenResponse sourceTokenResponse = solanaClient.fetchTokenInfo(sourceTokenKey);
                        sourceTokenKey = sourceTokenResponse.tokenList().symbol();

                        SolanaTokenResponse destinationTokenResponse = solanaClient.fetchTokenInfo(destinationTokenKey);
                        destinationTokenKey = destinationTokenResponse.tokenList().symbol();
                    } catch (RuntimeException e) {
                        log.info("No token info for token {} or {}", sourceTokenKey, destinationTokenKey);
                    }
                    log.info("Wallet update successful, sending changes to bot...");
                    sender.send(new WalletUpdateResponse(
                            wallet.getId(),
                            wallet.getNumber(),
                            data.signature(),
                            sourceTokenKey,
                            sourceTokenAmount,
                            destinationTokenKey,
                            destinationTokenAmount,
                            getUsers(wallet)
                    ));
                }
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

    private OffsetDateTime convertTimeFromUtc(Long utc) {
        return TimeConverter.convertFromUtcToOffsetDateTime(utc);
    }
}
