package com.maocq.model.exception;

import com.maocq.model.exception.message.ErrorMessage;
import com.maocq.model.exception.message.ExternalError;
import lombok.Getter;

@Getter
public class ExternalBusinessException extends BusinessException {

    private final ExternalError error;

    public ExternalBusinessException(ErrorMessage errorMessage, ExternalError error) {
        super(errorMessage);
        this.error = error;
    }
}
