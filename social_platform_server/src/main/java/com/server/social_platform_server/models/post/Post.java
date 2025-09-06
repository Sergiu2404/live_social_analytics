package com.server.social_platform_server.models.post;

import com.server.social_platform_server.models.comment.Comment;
import com.server.social_platform_server.models.reactions.CommentsReactions;
import com.server.social_platform_server.models.reactions.PostsReactions;
import com.server.social_platform_server.models.user.User;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //user is loaded only when the post is accessed (not when post is fetched)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String content;
    private String mediaUrl;
    private String classificationLabel;
    private double latitude;
    private double longitude;
    private ZonedDateTime createdAt;

    @OneToMany(
            mappedBy = "post", //binding field (from entity not db)
            cascade = CascadeType.ALL, //propagate deletes, saves, ...
            orphanRemoval = true //remove comments no longer attached
    )
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostsReactions> postReactions = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
