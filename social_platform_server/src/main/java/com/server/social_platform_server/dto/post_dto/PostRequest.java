package com.server.social_platform_server.dto.post_dto;

import com.server.social_platform_server.models.comment.Comment;
import com.server.social_platform_server.models.reactions.PostsReactions;
import com.server.social_platform_server.models.user.User;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostRequest {
    private Long userId;
    private String content;
    private String mediaUrl;
    private String classificationLabel;
    private double latitude;
    private double longitude;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getClassificationLabel() {
        return classificationLabel;
    }

    public void setClassificationLabel(String classificationLabel) {
        this.classificationLabel = classificationLabel;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
