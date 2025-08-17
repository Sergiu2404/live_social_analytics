package com.server.social_platform_server.models.message;

import com.server.social_platform_server.models.user.User;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "messages", indexes = {
        @Index(name="idx_msg_conversation_created", columnList="conversation_id, createdAt"),
        @Index(name="idx_msg_sender", columnList="sender_id")
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String mediaUrl;
    private ZonedDateTime createdAt;

}
