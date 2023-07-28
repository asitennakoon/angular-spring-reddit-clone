package com.thiranya.angularspringredditclone.mapper;

import com.thiranya.angularspringredditclone.dto.SubredditDto;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "postCount", expression = "java(getPostCount(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer getPostCount(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
    @Mapping(target = "createdDate", expression = "java(now())")
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}