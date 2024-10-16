package ru.hse.bot.service.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.hse.bot.configuration.ApplicationConfig;
import ru.hse.bot.dto.WalletUpdateResponse;
import ru.hse.bot.service.interfaces.MessageSender;

@RequiredArgsConstructor
public class PullerQueueProducer implements MessageSender {
    private final RabbitTemplate rabbitTemplate;

    private final ApplicationConfig applicationConfig;

    @Override
    public void send(WalletUpdateResponse update) {
        rabbitTemplate.convertAndSend(applicationConfig.exchangeName(), applicationConfig.queueName(), update);
    }
}
