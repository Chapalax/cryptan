package ru.hse.bot.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.bot.domain.models.Wallet;
import ru.hse.bot.dto.AddWalletRequest;
import ru.hse.bot.dto.ListWalletsResponse;
import ru.hse.bot.dto.RemoveWalletRequest;
import ru.hse.bot.dto.WalletResponse;
import ru.hse.bot.service.interfaces.WalletService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class PullerWalletsController {
    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<ListWalletsResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        List<Wallet> walletList = walletService.listAll(tgChatId);
        ArrayList<WalletResponse> responseList = new ArrayList<>();
        for (Wallet wallet : walletList) {
            responseList.add(new WalletResponse(wallet.getId(), wallet.getNumber()));
        }
        return new ResponseEntity<>(new ListWalletsResponse(responseList, responseList.size()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WalletResponse> postAddLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid @NotNull AddWalletRequest addWalletRequest
    ) {
        Wallet addedWallet = walletService.add(tgChatId, addWalletRequest.wallet());
        return new ResponseEntity<>(new WalletResponse(addedWallet.getId(), addedWallet.getNumber()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<WalletResponse> deleteLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @RequestBody @Valid @NotNull RemoveWalletRequest removeWalletRequest
    ) {
        Wallet wallet = walletService.remove(tgChatId, removeWalletRequest.wallet());
        return new ResponseEntity<>(new WalletResponse(wallet.getId(), wallet.getNumber()), HttpStatus.OK);
    }
}
