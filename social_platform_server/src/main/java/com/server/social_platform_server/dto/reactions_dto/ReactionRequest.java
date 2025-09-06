package com.server.social_platform_server.dto.reactions_dto;

import com.server.social_platform_server.models.reactions.ReactionType;

public class ReactionRequest {
    private Long userId;
    private Long targetId; //react for postId or for commentId
    private ReactionType reactionType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
