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

@RestController
@RequestMapping("api/subreddits")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Subreddits", description = "The Subreddits API. Contains all the operations that can be performed on subreddits.")
@AllArgsConstructor
public class SubredditController {
    private SubredditService subredditService;

    @PostMapping
    @Operation(summary = "Create Subreddit", description = "Create a subreddit for a specific topic")
    public ResponseEntity<SubredditDto> create(@RequestBody @Valid SubredditDto subredditDto) {
        return ResponseEntity.ok(subredditService.save(subredditDto));
    }

    @GetMapping
    @Operation(summary = "Get All Subreddits", description = "See all the subreddits currently stored in the database")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAll());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Subreddit", description = "Get the subreddit with the provided ID")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(subredditService.getSubreddit(id));
    }
}