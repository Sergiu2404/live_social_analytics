package com.server.social_platform_server.repositories.comment;

import com.server.social_platform_server.models.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.post.id = :postId")
    Page<Comment> findCommentsByPostId(Long postId, Pageable pageable);

    @Query("select c from Comment c where c.user.id = :userId")
    Page<Comment> findCommentsByUserId(Long userId, Pageable pageable);
}
