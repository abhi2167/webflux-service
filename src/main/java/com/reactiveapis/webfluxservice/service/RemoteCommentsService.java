package com.reactiveapis.webfluxservice.service;

import com.reactiveapis.webfluxservice.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class RemoteCommentsService {
    private final WebClient webClient;
    @Value("${endpoints.comments-service:https://jsonplaceholder.typicode.com/comments}")
    private String endpoint;

    public Mono<Comment> getCommentById(String id) {
        String apiPath = "/comments/".concat(id);
        String url = this.endpoint + apiPath;
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Comment.class)
                .log();
    }

    public Flux<Comment> getAllComments() {
        String apiPath = "/comments";
        String url = this.endpoint + apiPath;
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Comment.class)
                .retry(3);
    }
}
