package com.server.social_platform_server.services.post;

import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.follow.FollowRepository;
import com.server.social_platform_server.repositories.post.PostRepository;
import com.server.social_platform_server.services.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final AuthenticationService authenticationService;

    public PostService(PostRepository postRepository, FollowRepository followRepository, AuthenticationService authenticationService){
        this.postRepository = postRepository;
        this.followRepository = followRepository;
        this.authenticationService = authenticationService;
    }

    public Page<Post> getMyPosts(Pageable pageable){
        User currentConnectedUser = this.authenticationService.getCurrentUser();
        Long currentConnectedUserId = currentConnectedUser.getId();
        return postRepository.findByUserIdOrderByCreatedAtDesc(currentConnectedUserId, pageable);
    }

    @Transactional
    public Post createPost(String content, String mediaUrl, Double latitude, Double longitude){
        User currentUser = authenticationService.getCurrentUser();

        Post post = new Post();
        post.setContent(content);
        post.setMediaUrl(mediaUrl);
        post.setUser(currentUser);
        post.setClassificationLabel("DEFAULT");
        post.setLatitude(latitude != null ? latitude : 0.0);
        post.setLongitude(longitude != null ? longitude : 0.0);
        post.setCreatedAt(ZonedDateTime.now());

        return postRepository.save(post);
    }
}
