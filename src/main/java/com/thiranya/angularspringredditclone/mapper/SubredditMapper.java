package com.thiranya.angularspringredditclone.mapper;

import com.thiranya.angularspringredditclone.dto.SubredditDto;
import com.thiranya.angularspringredditclone.model.Subreddit;
import com.thiranya.angularspringredditclone.service.AuthService;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SubredditMapper {
    @Autowired
    protected AuthService authService;

    @Mapping(target = "postCount", expression = "java(subreddit.getPosts().size())")
    public abstract SubredditDto mapSubredditToDto(Subreddit subreddit);

    @InheritInverseConfiguration
    @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "posts", ignore = true)
    public abstract Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}