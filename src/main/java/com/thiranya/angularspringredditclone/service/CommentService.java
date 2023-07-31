package com.thiranya.angularspringredditclone.service;

import com.thiranya.angularspringredditclone.dto.CommentDto;
import com.thiranya.angularspringredditclone.exception.PostNotFoundException;
import com.thiranya.angularspringredditclone.mapper.CommentMapper;
import com.thiranya.angularspringredditclone.model.Comment;
import com.thiranya.angularspringredditclone.model.NotificationEmail;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.User;
import com.thiranya.angularspringredditclone.repository.CommentRepository;
import com.thiranya.angularspringredditclone.repository.PostRepository;
import com.thiranya.angularspringredditclone.repository.UserRepository;
import com.thiranya.angularspringredditclone.util.MailContentBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private PostRepository postRepository;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private MailContentBuilder mailContentBuilder;
    private MailService mailService;
    private UserRepository userRepository;

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));

        Comment comment = commentRepository.save(commentMapper.mapDtoToComment(commentDto, post));

        sendCommentNotification(comment.getUser().getUsername(), post);

        commentDto.setId(comment.getId());
        commentDto.setCreatedDate(comment.getCreatedDate());
        return commentDto;
    }

    private void sendCommentNotification(String commenter, Post post) {
        String message = mailContentBuilder.build(commenter + " posted a comment on your post." + post.getUrl());
        mailService.sendMail(new NotificationEmail(commenter + " commented on your post", post.getUser().getEmail(), message));
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapCommentToDto)
                .toList();
    }

    public List<CommentDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapCommentToDto)
                .toList();
    }
}