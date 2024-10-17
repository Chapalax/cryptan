package ru.hse.bot.web;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.hse.bot.web.dto.SolanaDataResponse;
import ru.hse.bot.web.dto.SolanaResponse;
import ru.hse.bot.web.interfaces.WebClientSolana;

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
    public SolanaDataResponse fetchWalletsTransactions(String wallet) {
        return Objects.requireNonNull(webClient.get()
                        .uri("/accounts/{wallet}/transactions?limit=100&page=1", wallet)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::isError,
                                clientResponse -> clientResponse.bodyToMono(RuntimeException.class)
                                        .flatMap(error -> Mono.error(new RuntimeException("Solana API Exception")))
                        )
                        .bodyToMono(SolanaResponse.class)
                        .block())
                .result()
                .data()
                .get(0);
    }
}
