package com.server.social_platform_server.controllers.post;

import com.server.social_platform_server.dto.post_dto.PostRequest;
import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.services.post.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public Page<Post> getAllPosts(Pageable pageable){
        return this.postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return this.postService.getPostById(id);
    }

    @PostMapping()
    public Post createPost(@RequestBody PostRequest request){
        return this.postService.createPost(
                request.getContent(),
                request.getMediaUrl(),
                request.getLatitude(),
                request.getLongitude()
        );
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        this.postService.deletePost(id);
    }

    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable Long id,
            @RequestBody PostRequest request
            ){
        Post existing = this.postService.getPostById(id);
        return this.postService.updatePost(existing.getId(), request.getContent(), request.getMediaUrl());
    }
}
