package com.reactiveapis.webfluxservice.repository;

import com.reactiveapis.webfluxservice.entity.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

}
