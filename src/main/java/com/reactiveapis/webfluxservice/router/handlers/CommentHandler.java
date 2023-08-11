package com.reactiveapis.webfluxservice.router.handlers;

import com.reactiveapis.webfluxservice.ResourceNotFoundException;
import com.reactiveapis.webfluxservice.entity.Comment;
import com.reactiveapis.webfluxservice.repository.CommentRepository;
import com.reactiveapis.webfluxservice.service.RemoteCommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentHandler {

    private final CommentRepository commentRepository;
    private final RemoteCommentsService remoteCommentsService;


    public Mono<ServerResponse> addCommentHandler(ServerRequest request) {
        return request.bodyToMono(Comment.class).flatMap(commentRepository :: save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED) :: bodyValue);
    }

    public Mono<ServerResponse> getCommentById(ServerRequest request) {
        String commentId = request.pathVariable("id");
        Mono<Comment> commentMono = commentRepository
                                    .findById(Long.valueOf(commentId))
                .                      switchIfEmpty(Mono.error(new ResourceNotFoundException("comment not found for id " + commentId)));
        return ServerResponse.ok().body(commentMono, Comment.class);
    }

    public Mono<ServerResponse> getCommentsHandler(ServerRequest request) {
        Flux<Comment> commentsFlux = commentRepository.findAll();
        return ServerResponse.ok().body(commentsFlux, Comment.class);
    }

    public Mono<ServerResponse> getAllCommentsFromRemoteService(ServerRequest request) {
        return  ServerResponse
                .ok()
                .body(
                        remoteCommentsService
                                .getAllComments()
                                .collectList()
                        , Comment.class);
    }

    public Mono<ServerResponse> getAllCommentByIdFromRemoteService(ServerRequest request) {
        return  ServerResponse
                .ok()
                .body(remoteCommentsService.getCommentById(request.pathVariable("id")), Comment.class);
    }
}
