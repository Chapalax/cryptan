package ru.hse.bot.web;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.hse.bot.web.dto.*;

public class PullerClient implements PullerWebClient {
    private final String REQUEST_HEADER = "Tg-Chat-Id";

    @Value("${baseurl.puller}")
    private String baseUrl;

    private final WebClient webClient;

    public PullerClient() {
        webClient = WebClient.create(baseUrl);
    }

    public PullerClient(@URL String url) {
        webClient = WebClient.create(url);
    }

    @Override
    public ListWalletsResponse getAllWallets(long tgChatId) {
        return webClient.get()
                .uri("/wallets")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                                .flatMap(error -> Mono.error(new ApiErrorResponse(
                                        error.getDescription(),
                                        error.getCode(),
                                        error.getExceptionName(),
                                        error.getExceptionMessage(),
                                        error.getStacktrace()))
                                )
                )
                .bodyToMono(ListWalletsResponse.class)
                .block();
    }

    @Override
    public WalletResponse addWallet(long tgChatId, AddWalletRequest addWalletRequest) {
        return webClient.post()
                .uri("/wallets")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .bodyValue(addWalletRequest)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new ApiErrorResponse(
                                error.getDescription(),
                                error.getCode(),
                                error.getExceptionName(),
                                error.getExceptionMessage(),
                                error.getStacktrace()))
                        )
                )
                .bodyToMono(WalletResponse.class)
                .block();
    }

    @Override
    public WalletResponse deleteWallet(long tgChatId, RemoveWalletRequest removeWalletRequest) {
        return webClient.method(HttpMethod.DELETE)
                .uri("/wallets")
                .header(REQUEST_HEADER, String.valueOf(tgChatId))
                .bodyValue(removeWalletRequest)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new ApiErrorResponse(
                                error.getDescription(),
                                error.getCode(),
                                error.getExceptionName(),
                                error.getExceptionMessage(),
                                error.getStacktrace()))
                        )
                )
                .bodyToMono(WalletResponse.class)
                .block();
    }

    @Override
    public HttpStatus registerChat(long id) {
        return webClient.post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new ApiErrorResponse(
                                error.getDescription(),
                                error.getCode(),
                                error.getExceptionName(),
                                error.getExceptionMessage(),
                                error.getStacktrace()))
                        )
                )
                .bodyToMono(HttpStatus.class)
                .block();
    }

    @Override
    public HttpStatus deleteChat(long id) {
        return webClient.delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new ApiErrorResponse(
                                error.getDescription(),
                                error.getCode(),
                                error.getExceptionName(),
                                error.getExceptionMessage(),
                                error.getStacktrace()))
                        )
                )
                .bodyToMono(HttpStatus.class)
                .block();
    }
}
