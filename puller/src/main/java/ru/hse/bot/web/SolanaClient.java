package ru.hse.bot.web;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.hse.bot.web.dto.accounts.SolanaAccountDataResponse;
import ru.hse.bot.web.dto.accounts.SolanaAccountResponse;
import ru.hse.bot.web.dto.tokens.SolanaTokenResponse;
import ru.hse.bot.web.dto.transfers.SolanaTransactionDataResponse;
import ru.hse.bot.web.dto.transfers.SolanaTransactionResponse;
import ru.hse.bot.web.interfaces.WebClientSolana;

import java.util.List;
import java.util.Objects;

public class SolanaClient implements WebClientSolana {
    @Value("${baseurl.solana}")
    private String solanaBaseUrl;

    private final WebClient webClient;

    public SolanaClient() {
        webClient = WebClient.create(solanaBaseUrl);
    }

    public SolanaClient(@URL String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public List<SolanaAccountDataResponse> fetchWalletsTransactions(String wallet, Long utcFrom) {
        return Objects.requireNonNull(webClient.get()
                        .uri("/v0/accounts/{wallet}/transactions?utcFrom={utcFrom}&page=1", wallet, utcFrom)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::isError,
                                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                                        .flatMap(error -> Mono.error(new RuntimeException("Solana API Exception")))
                        )
                        .bodyToMono(SolanaAccountResponse.class)
                        .block())
                .result()
                .data();
    }

    @Override
    public List<SolanaTransactionDataResponse> fetchTransactionInfo(String signature) {
        return Objects.requireNonNull(webClient.get()
                        .uri("/v0/transfers/{hash}", signature)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::isError,
                                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                                        .flatMap(error -> Mono.error(new RuntimeException("Solana API Exception")))
                        )
                        .bodyToMono(SolanaTransactionResponse.class)
                        .block())
                .result()
                .data();
    }

    @Override
    public SolanaTokenResponse fetchTokenInfo(String tokenHash) {
        return Objects.requireNonNull(webClient.get()
                .uri("/v1/tokens/{hash}", tokenHash)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Solana API Exception")))
                )
                .bodyToMono(SolanaTokenResponse.class)
                .block()
        );
    }
}
