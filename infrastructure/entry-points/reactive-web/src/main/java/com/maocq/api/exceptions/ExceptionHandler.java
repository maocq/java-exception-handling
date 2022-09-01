package com.maocq.api.exceptions;

import com.maocq.model.exception.BusinessException;
import com.maocq.model.exception.ExternalBusinessException;
import com.maocq.model.exception.TechnicalException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Order(-2)
@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties resources,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        return Mono.just(getError(request))
                .flatMap(Mono::error)
                .onErrorResume(ExternalBusinessException.class, error -> ServerResponse.badRequest().bodyValue(error.getError()))
                .onErrorResume(BusinessException.class, error -> ServerResponse.badRequest()
                        .bodyValue(Map.of("code", error.getErrorMessage().getCode())))
                .onErrorResume(TechnicalException.class, error -> ServerResponse.badRequest()
                        .bodyValue(Map.of("code", error.getErrorMessage().getCode())))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .cast(ServerResponse.class);
    }
}
