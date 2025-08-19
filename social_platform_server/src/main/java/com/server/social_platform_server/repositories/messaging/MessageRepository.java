package com.server.social_platform_server.repositories.messaging;

import com.server.social_platform_server.models.message.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findMessageByConversationIdOrderByCreatedAtAsc(Long conversationId, Pageable pageable);

    Optional<Message> findTopMessageByConversationIdOrderByCreatedAtDesc(Long conversationId);
}