package com.thiranya.angularspringredditclone.dto;

import com.thiranya.angularspringredditclone.model.VoteType;
import lombok.Data;

@Data
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}