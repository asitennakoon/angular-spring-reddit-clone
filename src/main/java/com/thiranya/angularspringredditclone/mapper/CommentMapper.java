package com.thiranya.angularspringredditclone.mapper;

import com.thiranya.angularspringredditclone.dto.CommentDto;
import com.thiranya.angularspringredditclone.model.Comment;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    protected AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
    @Mapping(target = "post", source = "post")
    public abstract Comment mapDtoToComment(CommentDto commentDto, Post post);

    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    public abstract CommentDto mapCommentToDto(Comment comment);
}