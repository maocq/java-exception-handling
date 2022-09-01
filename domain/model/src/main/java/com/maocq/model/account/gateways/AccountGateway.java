package com.maocq.model.account.gateways;

import com.maocq.model.account.Account;
import reactor.core.publisher.Mono;

public interface AccountGateway {

    Mono<Account> getAccount(String id);
}
