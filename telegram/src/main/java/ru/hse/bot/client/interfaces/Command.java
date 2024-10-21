package ru.hse.bot.client.interfaces;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;

public interface Command {
    String command();

    String description();

    SendMessage handle(@NotNull Update update);

    default boolean supports(@NotNull Update update) {
        return command().equals(update.message().text().substring(1));
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
