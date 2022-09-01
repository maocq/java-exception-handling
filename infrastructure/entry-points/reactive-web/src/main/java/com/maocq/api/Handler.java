package com.maocq.api;

import com.maocq.model.account.Account;
import com.maocq.usecase.createaccount.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreateAccountUseCase createAccountUseCase;

    public Mono<ServerResponse> listenGETHello(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("Hello");
    }

    public Mono<ServerResponse> listenGETAccountUseCase(ServerRequest serverRequest) {
        var id = serverRequest.queryParam("id")
                .orElse("3ac79ddc-09a6-40dd-bf5d-ca26c7363d7a");

        return ServerResponse.ok().body(createAccountUseCase.getAccount(id), Account.class);
    }
}
