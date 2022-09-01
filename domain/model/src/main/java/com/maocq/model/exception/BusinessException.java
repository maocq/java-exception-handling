package com.maocq.model.exception;

import com.maocq.model.exception.message.ErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final ErrorMessage errorMessage;
}
