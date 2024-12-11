package ru.hse.bot.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import io.prometheus.client.Counter;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.bot.client.interfaces.Bot;
import ru.hse.bot.client.interfaces.Command;
import ru.hse.bot.client.interfaces.UserMessageProcessor;
import ru.hse.bot.dto.WalletUpdateRequest;

import java.util.List;
import java.util.Map;

@Component
public class TrackerBot implements Bot {
    private static TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;
    private static final Counter messagesCounter = Counter.build()
            .name("bot_messages")
            .help("Total messages received/sent")
            .labelNames("thread")
            .register();

    @Autowired
    public TrackerBot(@NotNull UserMessageProcessor messageProcessor, @Value("${app.token}") String token) {
        this.userMessageProcessor = messageProcessor;
        bot = new TelegramBot(token);
    }

    @PostConstruct
    @Override
    public void start() {
        bot.setUpdatesListener(this);
        bot.execute(new SetMyCommands(userMessageProcessor
                .commands()
                .stream()
                .map(Command::toApiCommand)
                .toArray(BotCommand[]::new)));
    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

    @Override
    public int process(@NotNull List<Update> list) {
        for (Update update : list) {
            if (update.myChatMember() != null) {
                userMessageProcessor.deleteChat(update);
            } else {
                bot.execute(userMessageProcessor.process(update));
                messagesCounter.labels("income").inc();
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public static void sendUpdates(@NotNull WalletUpdateRequest updates) {
        for (Map.Entry<Long, String> tgChatInfo : updates.tgChatInfo().entrySet()) {
            StringBuilder message = new StringBuilder();
            bot.execute(new SendMessage(
                    tgChatInfo.getKey(),
                    message.append("*New transactions!*\n")
                            .append("\n*Wallet:*\n")
                            .append(tgChatInfo.getValue())
                            .append("\n\n*Transaction:*\n")
                            .append(updates.transaction())
                            .append("\n\n*Swapped* ")
                            .append(updates.sourceTokenAmount())
                            .append(" ")
                            .append(updates.sourceTokenKey())
                            .append(" on ")
                            .append(updates.destinationTokenAmount())
                            .append(" ")
                            .append(updates.destinationTokenKey())
                            .append("\nhttps://raydium.io/swap/")
                            .toString()
                    ).parseMode(ParseMode.Markdown)
            );
            messagesCounter.labels("outcome").inc();
        }
    }
}
