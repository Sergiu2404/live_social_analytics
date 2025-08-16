package com.server.social_platform_server.repositories.follow;

import com.server.social_platform_server.models.follow.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Followers, Long> {
    @Query("select f.followee.id from Followers f where f.follower.id = :followerId")
    List<Long> findFolloweeIds(Long followerId);

    boolean existsFollowerAndFolloweeIds(Long followerId, Long followeeId);
    void addFollowerAndFolloweeIds(Long followerId, Long followeeId);
    void deleteFollowerAndFolloweeIds(Long followerId, Long followeeId);
}
