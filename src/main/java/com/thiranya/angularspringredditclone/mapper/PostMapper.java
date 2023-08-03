package com.thiranya.angularspringredditclone.mapper;

import com.thiranya.angularspringredditclone.dto.PostDto;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.Subreddit;
import com.thiranya.angularspringredditclone.repository.CommentRepository;
import com.thiranya.angularspringredditclone.repository.VoteRepository;
import com.thiranya.angularspringredditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    protected AuthService authService;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected VoteRepository voteRepository;

    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapDtoToPost(PostDto postDto, Subreddit subreddit);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "commentCount", expression = "java(commentRepository.findByPost(post).size())")
    @Mapping(target = "duration", expression = "java(com.github.marlonlom.utilities.timeago.TimeAgo.using(post.getCreatedDate().toEpochMilli()))")
    public abstract PostDto mapPostToDto(Post post);
}