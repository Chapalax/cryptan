package ru.hse.bot.dto;

import java.util.ArrayList;

public record WalletUpdateResponse(
        long id,
        String wallet,
        String transaction,
        String sourceTokenKey,
        long sourceTokenAmount,
        String destinationTokenKey,
        long destinationTokenAmount,
        ArrayList<Long> tgChatIds
) { }
