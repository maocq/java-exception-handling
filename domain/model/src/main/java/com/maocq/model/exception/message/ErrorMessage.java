package com.maocq.model.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    ACCOUNT_FIND_ERROR("SFB0001", "Error consultando la cuenta"),
    CHANNEL_TRANSACTION_NOT_FOUND("SFT0002", "Canal transaccional no encontrado"),

    TECHNICAL_RESTCLIENT_ERROR("SCT0010","Ha ocurrido un error en el cliente rest"),
    EXTERNAR_MESSAGE_ERROR("SFB9999", "Error");


    private final String code;
    private final String message;
}
