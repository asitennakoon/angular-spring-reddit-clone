package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.PostDto;
import com.thiranya.angularspringredditclone.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/posts")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Posts", description = "The Posts API. Contains all the operations that can be performed on posts.")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @PostMapping
    @Operation(summary = "Create Post", description = "Create a post on a specific subreddit")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return status(CREATED).body(postService.save(postDto));
    }

    @GetMapping
    @Operation(summary = "Get All Posts", description = "See all the posts currently stored in the database")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return status(OK).body(postService.getAllPosts());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Post", description = "Get the post with the provided ID")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return status(OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    @Operation(summary = "Get Posts by Subreddit", description = "Get posts in the provided subreddit")
    public ResponseEntity<List<PostDto>> getPostsBySubreddit(Long id) {
        return status(OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("by-user/{username}")
    @Operation(summary = "Get Posts by Username", description = "Get posts added by the provided username")
    public ResponseEntity<List<PostDto>> getPostsByUsername(String username) {
        return status(OK).body(postService.getPostsByUsername(username));
    }
}