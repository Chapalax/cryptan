package ru.hse.bot.client.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.hse.bot.client.interfaces.Command;
import ru.hse.bot.web.PullerWebClient;
import ru.hse.bot.web.dto.ApiErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final String COMMAND = "start";
    private final String DESCRIPTION = "Run the bot";
    private final String ANSWER = "Thank you for using our bot!\n"
            + "To get information about the bot, use the command /help";

    private final PullerWebClient pullerWebClient;

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
        try {
            pullerWebClient.registerChat(update.message().chat().id());
            return new SendMessage(update.message().chat().id(), ANSWER);
        } catch (ApiErrorResponse errorResponse) {
            return new SendMessage(update.message().chat().id(), errorResponse.getDescription());
        }
    }
}
