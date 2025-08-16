package com.server.social_platform_server.models.follow;

import com.server.social_platform_server.models.user.User;
import jakarta.persistence.*;

@Entity
@Table(
        name="followers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "followee_id"}),
        indexes = {
                @Index(name = "idx_follows_follower", columnList = "follower_id"),
                @Index(name = "idx_follows_followee", columnList = "followee_id")
        }
)
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }
}
