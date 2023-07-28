package com.thiranya.angularspringredditclone.dto;

import lombok.Data;

@Data
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer postCount;
}