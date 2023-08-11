package com.reactiveapis.webfluxservice.router.configuration;

import com.reactiveapis.webfluxservice.router.handlers.CommentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerConfig(CommentHandler commentHandler) {
        return route()
                .GET("/hello-world", request -> ServerResponse.ok().bodyValue("Hello world").log())
                .POST("/comments", request -> commentHandler.addCommentHandler(request))
                .GET("/comments", request -> commentHandler.getCommentsHandler(request))
                .GET("/comments/{id}", request -> commentHandler.getCommentById(request))
                .GET("/remote/comments", request -> commentHandler.getAllCommentsFromRemoteService(request))
                .GET("/remote/comments/{id}", request -> commentHandler.getAllCommentByIdFromRemoteService(request))
                .build();
    }
}
