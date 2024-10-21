package ru.hse.bot.service.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.hse.bot.configuration.ApplicationConfig;
import ru.hse.bot.dto.WalletUpdateResponse;
import ru.hse.bot.service.interfaces.MessageSender;

@Slf4j
@RequiredArgsConstructor
public class PullerQueueProducer implements MessageSender {
    private final RabbitTemplate rabbitTemplate;

    private final ApplicationConfig applicationConfig;

    @Override
    public void send(WalletUpdateResponse update) {
        log.info("Sending updates...");
        rabbitTemplate.convertAndSend(applicationConfig.exchangeName(), applicationConfig.queueName(), update);
    }
}
