package com.server.social_platform_server.repositories.reactions;

import com.server.social_platform_server.models.reactions.CommentsReactions;
import com.server.social_platform_server.models.reactions.PostsReactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentsReactionsRepository extends JpaRepository<CommentsReactions, Long> {
    @Query("select cr from CommentsReactions cr where cr.comment.id = :commentId")
    Page<CommentsReactions> findReactionsForCommentId(Long commentId, Pageable pageable);

    @Query("select cr from CommentsReactions cr where cr.user.id = :userId")
    Page<CommentsReactions> findReactionsByUserId(Long userId, Pageable pageable);

    Optional<CommentsReactions> findByUserIdAndCommentId(Long userId, Long commentId);

    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
