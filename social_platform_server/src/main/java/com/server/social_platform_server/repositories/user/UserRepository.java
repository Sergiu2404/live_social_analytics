package com.server.social_platform_server.repositories.user;

import com.server.social_platform_server.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username = :username")
    User findByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
