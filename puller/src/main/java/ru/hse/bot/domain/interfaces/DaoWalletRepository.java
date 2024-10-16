package ru.hse.bot.domain.interfaces;

import ru.hse.bot.domain.models.Wallet;

import java.util.List;

public interface DaoWalletRepository extends Dao<Wallet> {
    Boolean isExists(Wallet wallet);

    Wallet findByNumber(Wallet wallet);

    Wallet findById(Wallet wallet);

    List<Wallet> findAllToUpdate();

    void update(Wallet wallet);
}
