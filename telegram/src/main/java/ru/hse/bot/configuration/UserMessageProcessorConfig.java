package ru.hse.bot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.bot.client.UserMessageHandler;
import ru.hse.bot.client.commands.*;
import ru.hse.bot.client.interfaces.UserMessageProcessor;
import ru.hse.bot.web.PullerWebClient;

@Configuration
public class UserMessageProcessorConfig {
    @Bean
    @Autowired
    public UserMessageProcessor userMessageProcessor (PullerWebClient pullerWebClient, StartCommand startCommand,
                                                      HelpCommand helpCommand, TrackCommand trackCommand,
                                                      UntrackCommand untrackCommand, ListCommand listCommand) {
        return new UserMessageHandler(
                pullerWebClient, startCommand, helpCommand,
                trackCommand, untrackCommand, listCommand
        );
    }
}
