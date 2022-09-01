package com.maocq.model.account;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Account {

    private Integer accountId;
    private String name;
}
