package ru.hse.bot.client.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.hse.bot.client.interfaces.Command;
import ru.hse.bot.web.PullerWebClient;
import ru.hse.bot.web.dto.ApiErrorResponse;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final String COMMAND = "list";
    private final String DESCRIPTION = "Show list of tracked wallets";
    private final String ANSWER = "The list of wallets you are tracking:\n";
    private final String WARNING = "You are not tracking any wallets yet!\n"
            + "To start tracking transactions, use the command /track";

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
            var listWalletsResponse = pullerWebClient.getAllWallets(update.message().chat().id());
            if (listWalletsResponse.size() == 0) {
                return new SendMessage(update.message().chat().id(), WARNING);
            }
            var message = new StringBuilder();
            message.append(ANSWER);
            for (int i = 0; i < listWalletsResponse.size(); i++) {
                message.append(listWalletsResponse.wallets().get(i).wallet()).append("\n");
            }
            return new SendMessage(update.message().chat().id(), message.toString());
        } catch (ApiErrorResponse errorResponse) {
            return new SendMessage(update.message().chat().id(), errorResponse.getDescription());
        }
    }
}
