package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.SubredditDto;
import com.thiranya.angularspringredditclone.service.SubredditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/subreddits")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Subreddits", description = "The Subreddits API. Contains all the operations that can be performed on subreddits.")
@AllArgsConstructor
public class SubredditController {
    private SubredditService subredditService;

    @PostMapping
    @Operation(summary = "Create Subreddit", description = "Create a subreddit for a specific topic")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody @Valid SubredditDto subredditDto) {
        return status(CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    @Operation(summary = "Get All Subreddits", description = "See all the subreddits currently stored in the database")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return status(OK).body(subredditService.getAll());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Subreddit", description = "Get the subreddit with the provided ID")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return status(OK).body(subredditService.getSubreddit(id));
    }
}