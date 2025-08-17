package com.server.social_platform_server.services.post;

import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.follow.FollowRepository;
import com.server.social_platform_server.repositories.post.PostRepository;
import com.server.social_platform_server.services.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public void deletePost(Long postId){
        User currentUser = authenticationService.getCurrentUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    @Transactional
    public Post updatePost(Long postId, String newContent){
        if (newContent == null || newContent.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content must not be empty.");
        }

        User currentUser = authenticationService.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this post.");
        }

        post.setContent(newContent.trim());
        return postRepository.save(post);
    }
}
