package com.maocq.usecase.createaccount;

import com.maocq.model.account.Account;
import com.maocq.model.account.gateways.AccountGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateAccountUseCase {
    private final AccountGateway accountGateway;

    public Mono<Account> getAccount(String id) {
        return accountGateway.getAccount(id);
    }
}
