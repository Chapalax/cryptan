package ru.hse.bot.domain.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.bot.domain.interfaces.DaoWalletRepository;
import ru.hse.bot.domain.jpa.JpaWalletRepository;
import ru.hse.bot.domain.models.Wallet;
import ru.hse.bot.exceptions.WalletNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class WalletRepository implements DaoWalletRepository {
    @Value("${check.interval}")
    private Long checkInterval;

    private final JpaWalletRepository walletRepository;

    @Override
    @Transactional
    public Boolean isExists(String number) {
        return walletRepository.existsByNumber(number);
    }

    @Override
    @Transactional
    public Wallet findByNumber(String number) {
        return walletRepository.findWalletByNumber(number);
    }

    @Override
    @Transactional
    public Wallet findById(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found."));
    }

    @Override
    @Transactional
    public List<Wallet> findAllToUpdate() {
        return walletRepository.findAllByCheckedAtBefore(OffsetDateTime.now().minusMinutes(checkInterval));
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
