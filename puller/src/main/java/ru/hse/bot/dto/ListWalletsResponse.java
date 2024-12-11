package ru.hse.bot.dto;

import java.util.HashMap;

public record ListWalletsResponse(HashMap<String, String> walletsWithNames, int size) {
}
