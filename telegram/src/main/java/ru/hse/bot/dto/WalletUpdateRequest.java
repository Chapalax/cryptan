package ru.hse.bot.dto;

import java.util.ArrayList;

public record WalletUpdateRequest(long id, String wallet, String description, ArrayList<Long> tgChatIds) {
}
