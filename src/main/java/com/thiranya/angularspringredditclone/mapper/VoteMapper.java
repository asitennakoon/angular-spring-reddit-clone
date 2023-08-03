package com.thiranya.angularspringredditclone.mapper;

import com.thiranya.angularspringredditclone.dto.VoteDto;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.User;
import com.thiranya.angularspringredditclone.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class VoteMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    public abstract Vote mapDtoToVote(VoteDto voteDto, User user, Post post);

    @Mapping(target = "postId", expression = "java(vote.getPost().getPostId())")
    public abstract VoteDto mapVoteToDto(Vote vote);
}