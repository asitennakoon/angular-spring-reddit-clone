package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.VoteDto;
import com.thiranya.angularspringredditclone.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/votes")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Votes", description = "The Votes API. Contains the operation that can be performed to upvote or downvote a post.")
@AllArgsConstructor
public class VoteController {
    private VoteService voteService;

    @PostMapping
    @Operation(summary = "Vote", description = "Upvote or downvote a post")
    public ResponseEntity<HttpStatus> vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return status(OK).build();
    }
}