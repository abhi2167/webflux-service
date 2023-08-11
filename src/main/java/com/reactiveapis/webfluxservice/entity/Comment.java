package com.reactiveapis.webfluxservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
public class Comment {

    @Id
    private Long id;
    @Column("post_id")
    private  Long postId;
    private String name;
    private String email;
    private String body;
}
