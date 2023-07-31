package com.thiranya.angularspringredditclone.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private Instant createdDate;
    private String username;
    private Long postId;
}