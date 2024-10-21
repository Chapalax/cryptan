package ru.hse.bot.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hse.bot.service.interfaces.WalletUpdater;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class WalletUpdaterScheduler {
    private final WalletUpdater walletUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("Looking for updates...");
        walletUpdater.update();
        log.info("All wallets are up to update.");
    }
}
