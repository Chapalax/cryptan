package ru.hse.bot.dto;

import java.util.HashMap;

public record WalletUpdateRequest(
        long id,
        String wallet,
        String transaction,
        String sourceTokenKey,
        double sourceTokenAmount,
        String destinationTokenKey,
        double destinationTokenAmount,
        HashMap<Long, String> tgChatInfo
) { }
