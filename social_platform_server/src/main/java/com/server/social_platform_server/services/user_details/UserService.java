package com.server.social_platform_server.services.user_details;

import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public Page<User> getAllUsers(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }

    public Page<User> searchUsersByUsernameContainingSubstring(String usernameSubstring, Pageable pageable){
        return this.userRepository.findByUsernameContainingIgnoreCase(usernameSubstring, pageable);
    }
}
