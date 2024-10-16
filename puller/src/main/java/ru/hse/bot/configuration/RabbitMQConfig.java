package ru.hse.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.bot.service.interfaces.MessageSender;
import ru.hse.bot.service.producers.PullerQueueProducer;

@Slf4j
@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange directExchange(@NotNull ApplicationConfig applicationConfig) {
        log.info("Creating RabbitMQ beans... ");
        return new DirectExchange(applicationConfig.exchangeName());
    }

    @Bean
    public Queue queue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName())
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", applicationConfig.queueName() + ".dlq")
                .build();
    }

    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .withQueueName();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageSender messageSender(
            @NotNull RabbitTemplate rabbitTemplate,
            @NotNull ApplicationConfig applicationConfig
    ) {
        log.info("Creating QueueProducer bean...");
        return new PullerQueueProducer(rabbitTemplate, applicationConfig);
    }
}
