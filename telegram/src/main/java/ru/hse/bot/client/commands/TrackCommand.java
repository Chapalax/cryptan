package ru.hse.bot.client.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.hse.bot.client.interfaces.Command;

@Component
public class TrackCommand implements Command {
    private final String COMMAND = "track";
    private final String DESCRIPTION = "Start tracking wallet";
    private final String ANSWER = "Enter a wallet number you are interested in:";
    private final String PLACEHOLDER = "nix9RErnQYXpuf329ntPgSzThogtfeyUempD7zDrnSH";

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(@NotNull Update update) {
        return new SendMessage(update.message().chat().id(), ANSWER)
                .replyMarkup(new ForceReply().inputFieldPlaceholder(PLACEHOLDER));
    }
}
