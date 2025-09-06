package com.server.social_platform_server.controllers.comment;

import com.server.social_platform_server.dto.comment_dto.CommentRequest;
import com.server.social_platform_server.models.comment.Comment;
import com.server.social_platform_server.services.comment.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/post")
    public Page<Comment> getCommentsByPost(
            @RequestParam Long postId,
            Pageable pageable
    ){
        return commentService.getCommentsByPostId(postId, pageable);
    }
    @GetMapping("/user")
    public Page<Comment> getCommentsByUser(
            @RequestParam Long userId,
            Pageable pageable
    ){
        return commentService.getCommentsByUserId(userId, pageable);
    }

    @PostMapping
    public Comment createComment(@RequestBody CommentRequest request){
        return this.commentService.saveComment(request.getText(), request.getUserId(), request.getPostId());
    }

    @PutMapping("/{id}")
    public Comment updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request
    ){
        Comment existing = commentService.getCommentById(id);
        return commentService.saveComment(request.getText(), existing.getUser().getId(), existing.getPost().getId());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        this.commentService.deleteCommentById(id);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id){
        return this.commentService.getCommentById(id);
    }
}
