package ru.hse.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.bot.client.consumers.PullerQueueListener;
import ru.hse.bot.dto.WalletUpdateRequest;

import java.util.HashMap;
import java.util.Map;

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
    public Queue deadLetterQueue(@NotNull ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName() + ".dlq").build();
    }

    @Bean
    public Binding binding(@NotNull Queue queue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .withQueueName();
    }

    @Bean
    public Binding deadLetterBinding(@NotNull Queue deadLetterQueue, @NotNull DirectExchange directExchange) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(directExchange)
                .with(deadLetterQueue.getName() + ".dlq");
    }

    @Bean
    public PullerQueueListener scrapperQueueListener() {
        return new PullerQueueListener();
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.hse.bot.dto.WalletUpdateResponse", WalletUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.hse.bot.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
