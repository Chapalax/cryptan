package ru.hse.bot.client.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.hse.bot.client.TrackerBot;
import ru.hse.bot.dto.WalletUpdateRequest;

@RabbitListener(queues = "${app.queue-name}")
public class PullerQueueListener {
    @RabbitHandler
    public void receiver(WalletUpdateRequest walletUpdateRequest) {
        TrackerBot.sendUpdates(walletUpdateRequest);
    }
}
