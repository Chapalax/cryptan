package ru.hse.bot.client;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.hse.bot.client.interfaces.Command;
import ru.hse.bot.client.interfaces.UserMessageProcessor;
import ru.hse.bot.web.PullerWebClient;
import ru.hse.bot.web.dto.AddWalletRequest;
import ru.hse.bot.web.dto.ApiErrorResponse;
import ru.hse.bot.web.dto.RemoveWalletRequest;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class UserMessageHandler implements UserMessageProcessor {
    private final String WARNING = "This command does not exist.\nList of available commands: /help";
    private final String SUCCESSFUL_TRACK = "The wallet has been successfully added!";
    private final String SUCCESSFUL_UNTRACK = "The wallet has been successfully deleted!";
    private final String REPLY_TRACK = "In response to this message, enter the wallet number you are interested in:";
    private final String REPLY_UNTRACK = "In response to this message, enter the number of the wallet whose transactions you want to unsubscribe from:";

    private final PullerWebClient pullerClient;

    private final List<? extends Command> commands;

    public UserMessageHandler(@NotNull PullerWebClient pullerClient, @NotNull Command... commands) {
        this.pullerClient = pullerClient;
        this.commands = Arrays.stream(commands).toList();
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (var command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        if (isReplyTrack(update)) {
            try {
                pullerClient.addWallet(update.message().chat().id(), new AddWalletRequest(update.message().text()));
                return createSendMessage(update, SUCCESSFUL_TRACK);
            } catch (ApiErrorResponse errorResponse) {
                return createSendMessage(update, errorResponse.getDescription());
            }
        }
        if (isReplyUntrack(update)) {
            try {
                pullerClient.deleteWallet(
                        update.message().chat().id(),
                        new RemoveWalletRequest(update.message().text())
                );
                return createSendMessage(update, SUCCESSFUL_UNTRACK);
            } catch (ApiErrorResponse errorResponse) {
                return createSendMessage(update, errorResponse.getDescription());
            }
        }
        return createSendMessage(update, WARNING);
    }

    @Override
    public void deleteChat(@NotNull Update update) {
        try {
            pullerClient.deleteChat(update.myChatMember().chat().id());
            log.info("User: {} has blocked the bot.", update.myChatMember().chat().id().toString());
        } catch (ApiErrorResponse errorResponse) {
            log.warn("User: {} not registered.", update.myChatMember().chat().id().toString());
        }
    }

    private SendMessage createSendMessage(@NotNull Update update, String message) {
        return new SendMessage(update.message().chat().id(), message);
    }

    private boolean isReplyTrack(@NotNull Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_TRACK);
    }

    private boolean isReplyUntrack(@NotNull Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_UNTRACK);
    }
}
