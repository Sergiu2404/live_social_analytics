package com.server.social_platform_server.dto.chat_dto;

import java.time.ZonedDateTime;

//record -> immutable data class
public record MessageDTO(
        Long id,
        Long conversationId,
        Long senderId,
        String content,
        String mediaUrl,
        ZonedDateTime createdAt
) {}

