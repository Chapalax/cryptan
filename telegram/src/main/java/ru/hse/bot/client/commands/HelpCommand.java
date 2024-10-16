package ru.hse.bot.client.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.hse.bot.client.interfaces.Command;

@Component
public class HelpCommand implements Command {
    private final String COMMAND = "help";
    private final String DESCRIPTION = "Show list of available commands";
    private final String ANSWER = "I can help you track transactions for wallets you are interested in.\n\n"
            + "*General bot commands:*\n"
            + "/start - run the bot\n"
            + "/help - information about the bot\n\n"
            + "*Tracking:*\n"
            + "/track - subscribe to transactions\n"
            + "/untrack - unsubscribe from transactions\n"
            + "/list - get a list of tracked wallets";

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
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true);
    }
}
