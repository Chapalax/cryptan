package ru.hse.bot.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.bot.domain.interfaces.DaoWalletRepository;
import ru.hse.bot.domain.jpa.JpaWalletRepository;
import ru.hse.bot.domain.models.Wallet;
import ru.hse.bot.exceptions.WalletNotFoundException;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class WalletRepository implements DaoWalletRepository {
    @Value("${check.interval}")
    private Long checkInterval;

    private final JpaWalletRepository walletRepository;

    @Override
    @Transactional
    public Boolean isExists(Wallet wallet) {
        return walletRepository.existsByNumber(wallet.getNumber());
    }

    @Override
    @Transactional
    public Wallet findByNumber(Wallet wallet) {
        return walletRepository.findWalletByNumber(wallet.getNumber());
    }

    @Override
    @Transactional
    public Wallet findById(Wallet wallet) {
        return walletRepository.findById(wallet.getId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found."));
    }

    @Override
    @Transactional
    public List<Wallet> findAllToUpdate() {
        return walletRepository.findAllByCheckedAtBefore(Instant.now().minusSeconds(checkInterval * 60));
    }

    @Override
    @Transactional
    public void update(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet add(Wallet object) {
        return walletRepository.save(object);
    }

    @Override
    @Transactional
    public int remove(Wallet object) {
        if (walletRepository.existsByNumber(object.getNumber())) {
            walletRepository.deleteByNumber(object.getNumber());
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public void flush() {
        walletRepository.flush();
    }
}
