package com.server.social_platform_server.services.messaging;

import com.server.social_platform_server.dto.chat_dto.ConversationSummaryDTO;
import com.server.social_platform_server.models.message.Conversation;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.messaging.ConversationRepository;
import com.server.social_platform_server.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepo;
    private final UserRepository userRepo;

    public ConversationService(ConversationRepository conversationRepo, UserRepository userRepo) {
        this.conversationRepo = conversationRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Conversation getOrCreate(Long userId1, Long userId2) {
        if (userId1.equals(userId2)) {
            throw new IllegalArgumentException("Cannot create a conversation with yourself.");
        }
        return conversationRepo.findConversationsBetweenUsers(userId1, userId2)
                .orElseGet(() -> {
                    User u1 = userRepo.findById(userId1)
                            .orElseThrow(() -> new EntityNotFoundException("User " + userId1 + " not found"));
                    User u2 = userRepo.findById(userId2)
                            .orElseThrow(() -> new EntityNotFoundException("User " + userId2 + " not found"));

                    Conversation c = new Conversation();
                    c.setUser1(u1);
                    c.setUser2(u2);
                    ZonedDateTime now = ZonedDateTime.now();
                    c.setCreatedAt(now);
                    c.setLastMessageAt(now); // set on creation; will be updated on messages
                    return conversationRepo.save(c);
                });
    }

    @Transactional(readOnly = true)
    public Page<ConversationSummaryDTO> listForUser(Long viewerId, Pageable pageable) {
        return conversationRepo.findAllConversationsForUser(viewerId, pageable)
                .map(c -> {
                    var other = c.getUser1().getId().equals(viewerId) ? c.getUser2() : c.getUser1();
                    String preview = null;
                    ZonedDateTime lastAt = c.getLastMessageAt();
                    return new ConversationSummaryDTO(
                            c.getId(),
                            other.getId(),
                            other.getUsername(), // adjust to your field
                            preview,
                            lastAt
                    );
                });
    }

    @Transactional
    public void touchLastMessageAt(Conversation conversation, ZonedDateTime when) {
        conversation.setLastMessageAt(when);
        conversationRepo.save(conversation);
    }
}
