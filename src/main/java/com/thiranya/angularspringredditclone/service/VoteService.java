package com.thiranya.angularspringredditclone.service;

import com.thiranya.angularspringredditclone.dto.VoteDto;
import com.thiranya.angularspringredditclone.exception.PostNotFoundException;
import com.thiranya.angularspringredditclone.exception.RedditException;
import com.thiranya.angularspringredditclone.mapper.VoteMapper;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.Vote;
import com.thiranya.angularspringredditclone.repository.PostRepository;
import com.thiranya.angularspringredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.thiranya.angularspringredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private PostRepository postRepository;
    private VoteRepository voteRepository;
    private AuthService authService;
    private VoteMapper voteMapper;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(voteMapper.mapDtoToVote(voteDto, authService.getCurrentUser(), post));
        postRepository.save(post);
    }
}