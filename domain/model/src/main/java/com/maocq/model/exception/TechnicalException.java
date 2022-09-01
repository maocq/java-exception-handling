package com.maocq.model.exception;

import com.maocq.model.exception.message.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicalException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public TechnicalException(Throwable cause, ErrorMessage errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
    }
}