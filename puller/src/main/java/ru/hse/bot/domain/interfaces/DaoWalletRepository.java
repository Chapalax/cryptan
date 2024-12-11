package ru.hse.bot.domain.interfaces;

import ru.hse.bot.domain.models.Wallet;

import java.util.List;

public interface DaoWalletRepository extends Dao<Wallet> {
    Boolean isExists(String number);

    Wallet findByNumber(String number);

    Wallet findById(Long id);

    List<Wallet> findAllToUpdate();

    void update(Wallet wallet);
}
