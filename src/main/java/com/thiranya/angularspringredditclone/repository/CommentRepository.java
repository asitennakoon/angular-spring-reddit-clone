package com.thiranya.angularspringredditclone.repository;

import com.thiranya.angularspringredditclone.model.Comment;
import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}