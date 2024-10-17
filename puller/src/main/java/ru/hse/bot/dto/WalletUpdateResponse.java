package ru.hse.bot.dto;

import java.util.ArrayList;

public record WalletUpdateResponse(long id, String wallet, String description, ArrayList<Long> tgChatIds) {
}
