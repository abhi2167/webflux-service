package com.reactiveapis.webfluxservice.router.handlers;

import com.reactiveapis.webfluxservice.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Exception :: {}", ex);
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer errorMessage = bufferFactory.wrap(ex.getMessage().getBytes());
        if(ex instanceof ResourceNotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return exchange.getResponse().writeWith(Mono.just(errorMessage));
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().writeWith(Mono.just(errorMessage));
        }
    }
}
