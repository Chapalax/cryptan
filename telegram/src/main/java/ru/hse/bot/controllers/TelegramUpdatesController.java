package ru.hse.bot.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.bot.client.TrackerBot;
import ru.hse.bot.dto.WalletUpdateRequest;

@RestController
@RequestMapping("/updates")
public class TelegramUpdatesController {
    @PostMapping
    public ResponseEntity<HttpStatus> postSendUpdate(@RequestBody @Valid WalletUpdateRequest walletUpdate) {
        TrackerBot.sendUpdates(walletUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
