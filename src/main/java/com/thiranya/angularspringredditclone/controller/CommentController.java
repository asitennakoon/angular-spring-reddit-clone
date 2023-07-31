package com.thiranya.angularspringredditclone.controller;

import com.thiranya.angularspringredditclone.dto.CommentDto;
import com.thiranya.angularspringredditclone.service.CommentService;
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
@RequestMapping("api/comments")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Comments", description = "The Comments API. Contains all the operations that can be performed on comments.")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @PostMapping
    @Operation(summary = "Create Comment", description = "Add a comment to a specific post")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return status(CREATED).body(commentService.save(commentDto));
    }

    @GetMapping("by-post/{id}")
    @Operation(summary = "Get Comments By Post", description = "Get all comments under a post using the Post ID")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(Long id) {
        return status(OK).body(commentService.getCommentsByPost(id));
    }

    @GetMapping("by-user/{username}")
    @Operation(summary = "Get Comments By User", description = "Get all comments posted by a user")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(String username) {
        return status(OK).body(commentService.getCommentsByUser(username));
    }
}