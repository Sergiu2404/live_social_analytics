package com.server.social_platform_server.dto.chat_dto;

import java.time.ZonedDateTime;

// record -> immutable data class
public record ConversationSummaryDTO(
        Long conversationId,
        Long otherUserId,
        String otherUserName,
        String lastMessagePreview,
        ZonedDateTime lastMessageAt
) {}
