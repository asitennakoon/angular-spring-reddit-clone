package com.thiranya.angularspringredditclone.repository;

import com.thiranya.angularspringredditclone.model.Post;
import com.thiranya.angularspringredditclone.model.User;
import com.thiranya.angularspringredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}