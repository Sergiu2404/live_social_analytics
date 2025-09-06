package com.server.social_platform_server.repositories.reactions;

import com.server.social_platform_server.models.reactions.PostsReactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostsReactionsRepository extends JpaRepository<PostsReactions, Long> {
    @Query("select pr from PostsReactions pr where pr.post.id = :postId")
    Page<PostsReactions> findReactionsForPostId(Long postId, Pageable pageable);

    @Query("select pr from PostsReactions pr where pr.user.id = :userId")
    Page<PostsReactions> findReactionsByUserId(Long userId, Pageable pageable);

    Optional<PostsReactions> findByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
