package ru.hse.bot.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;

public record ListWalletsResponse(@NotNull HashMap<String, String> walletsWithNames, int size) {
}
