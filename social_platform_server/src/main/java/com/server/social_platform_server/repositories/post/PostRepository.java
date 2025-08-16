package com.server.social_platform_server.repositories.post;

import com.server.social_platform_server.models.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
