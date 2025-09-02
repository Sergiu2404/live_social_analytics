package com.server.social_platform_server.repositories.follow;

import com.server.social_platform_server.models.follow.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Followers, Long> {
    @Query("select f.followee.id from Followers f where f.follower.id = :followerId")
    List<Long> findFolloweeIds(Long followerId);

    // spring data creates the queries by deleteBy..., findBy..., deleteBy... and then the name of the fields
    // (followerId because jpql searches for the entity and field names in java not the ones that will be in postgres)
    boolean existsByFollowerIdAndFolloweeId(@Param("followerId") Long followerId, @Param("followeeId") Long followeeId);
    @Modifying
    @Query("delete from Followers f where f.follower.id = :followerId and f.followee.id = :followeeId")
    void deleteByFollowerAndFolloweeIds(@Param("followerId") Long followerId, @Param("followee_id") Long followeeId);
}
