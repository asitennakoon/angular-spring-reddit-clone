package com.thiranya.angularspringredditclone.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private String username;
    private String subredditName;
}