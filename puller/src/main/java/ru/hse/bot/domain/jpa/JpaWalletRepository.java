package ru.hse.bot.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.bot.domain.models.Wallet;

import java.time.Instant;
import java.util.List;

@Repository
public interface JpaWalletRepository extends JpaRepository<Wallet, Long> {
    Boolean existsByNumber(String number);

    Wallet findWalletByNumber(String number);

    void deleteByNumber(String number);

    List<Wallet> findAllByCheckedAtBefore(Instant time);
}
