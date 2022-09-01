package com.maocq.consumer;

import com.maocq.model.account.Account;
import com.maocq.model.account.gateways.AccountGateway;
import com.maocq.model.exception.BusinessException;
import com.maocq.model.exception.ExternalBusinessException;
import com.maocq.model.exception.TechnicalException;
import com.maocq.model.exception.message.ExternalError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

import static com.maocq.model.exception.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class RestConsumer implements AccountGateway {

    private final WebClient client;

    public Mono<Account> getAccount(String id) {

        return client
            .get()
            .uri("/v3/{id}", id)
            //.exchangeToMono(...)
            .retrieve()
            .onStatus(httpStatus -> httpStatus.value() == 409, clientResponse ->
                    clientResponse.bodyToMono(ExternalError.class)
                    .flatMap(error -> Mono.error(new ExternalBusinessException(EXTERNAR_MESSAGE_ERROR, error))))
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BusinessException(ACCOUNT_FIND_ERROR)))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BusinessException(CHANNEL_TRANSACTION_NOT_FOUND)))
            .bodyToMono(AccountDto.class)
            .map(dto -> Account.builder()
                    .accountId(dto.getAccountId())
                    .name(dto.getName())
                    .build())
            .onErrorMap(TimeoutException.class, ex -> new TechnicalException(ex, TECHNICAL_RESTCLIENT_ERROR));
    }
}