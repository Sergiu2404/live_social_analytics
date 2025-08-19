package com.server.social_platform_server.repositories.messaging;

import com.server.social_platform_server.models.message.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("""
        select c from Conversation c 
        where (c.user1.id = :u1 and c.user2.id = :u2) or (c.user1.id = :u2 and c.user2.id = :u1) 
    """)
    Optional<Conversation> findConversationsBetweenUsers(
            @Param("u1") Long userId1,
            @Param("u2") Long userId2
    );

    @Query("""
           select c from Conversation c
           where c.user1.id = :userId or c.user2.id = :userId
           order by c.lastMessageAt desc nulls last, c.createdAt desc
    """)
    Page<Conversation> findAllConversationsForUser(@Param("userId") Long userId, Pageable pageable);

    @Query("""
           select (count(c) > 0) from Conversation c
           where (c.user1.id = :u1 and c.user2.id = :u2)
              or (c.user1.id = :u2 and c.user2.id = :u1)
    """)
    boolean existsConversationBetweenUsers(@Param("u1") Long userId1, @Param("u2") Long userId2);
}
