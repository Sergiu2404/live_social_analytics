package com.server.social_platform_server.services.messaging;

import com.server.social_platform_server.dto.chat_dto.MessageDTO;
import com.server.social_platform_server.models.message.Conversation;
import com.server.social_platform_server.models.message.Message;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.messaging.ConversationRepository;
import com.server.social_platform_server.repositories.messaging.MessageRepository;
import com.server.social_platform_server.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepo;
    private final ConversationRepository conversationRepo;
    private final UserRepository userRepo;
    private final ConversationService conversationService;

    public MessageService(
            MessageRepository messageRepo,
            ConversationRepository conversationRepo,
            UserRepository userRepo,
            ConversationService conversationService
    ) {
        this.messageRepo = messageRepo;
        this.conversationRepo = conversationRepo;
        this.userRepo = userRepo;
        this.conversationService = conversationService;
    }

    /**
     * Send a message by explicitly referring to a conversation.
     * Validates that sender is a participant.
     */
    @Transactional
    public MessageDTO send(Long conversationId, Long senderId, String content, String mediaUrl) {
        Conversation c = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found"));

        if (!c.getUser1().getId().equals(senderId) && !c.getUser2().getId().equals(senderId)) {
            throw new IllegalArgumentException("Sender is not a participant in this conversation");
        }

        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        ZonedDateTime now = ZonedDateTime.now();

        Message m = new Message();
        m.setConversation(c);
        m.setSender(sender);
        m.setContent(content);
        m.setMediaUrl(mediaUrl);
        m.setCreatedAt(now);

        Message saved = messageRepo.save(m);

        // update last activity on conv
        c.setLastMessageAt(now);
        conversationRepo.save(c);

        return new MessageDTO(saved.getId(), c.getId(), senderId, saved.getContent(), saved.getMediaUrl(), saved.getCreatedAt());
    }

    /**
     * send a message to a user (auto-creates/fetches conversation).
     */
    @Transactional
    public MessageDTO sendToUser(Long senderId, Long recipientId, String content, String mediaUrl) {
        Conversation c = conversationService.getOrCreate(senderId, recipientId);
        return send(c.getId(), senderId, content, mediaUrl);
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO> getMessages(Long conversationId, Pageable pageable) {
        return messageRepo.findMessageByConversationIdOrderByCreatedAtAsc(conversationId, pageable)
                .map(m -> new MessageDTO(
                        m.getId(),
                        m.getConversation().getId(),
                        m.getSender().getId(),
                        m.getContent(),
                        m.getMediaUrl(),
                        m.getCreatedAt()
                ));
    }

    @Transactional(readOnly = true)
    public MessageDTO getLastMessage(Long conversationId) {
        var last = messageRepo.findTopMessageByConversationIdOrderByCreatedAtDesc(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("No messages yet"));
        return new MessageDTO(
                last.getId(),
                last.getConversation().getId(),
                last.getSender().getId(),
                last.getContent(),
                last.getMediaUrl(),
                last.getCreatedAt()
        );
    }
}

