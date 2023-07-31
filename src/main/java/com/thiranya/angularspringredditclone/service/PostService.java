package com.thiranya.angularspringredditclone.service;

import com.thiranya.angularspringredditclone.dto.PostDto;
import com.thiranya.angularspringredditclone.exception.PostNotFoundException;
import com.thiranya.angularspringredditclone.exception.SubredditNotFoundException;
import com.thiranya.angularspringredditclone.mapper.PostMapper;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.Subreddit;
import com.thiranya.angularspringredditclone.model.User;
import com.thiranya.angularspringredditclone.repository.PostRepository;
import com.thiranya.angularspringredditclone.repository.SubredditRepository;
import com.thiranya.angularspringredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;
    private PostMapper postMapper;
    private SubredditRepository subredditRepository;
    private UserRepository userRepository;

    @Transactional
    public void save(PostDto postDto) {
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postDto.getSubredditName()));

        postRepository.save(postMapper.mapDtoToPost(postDto, subreddit));
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapPostToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));

        return postMapper.mapPostToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapPostToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapPostToDto)
                .toList();
    }
}