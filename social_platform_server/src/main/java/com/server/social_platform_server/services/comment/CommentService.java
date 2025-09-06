package com.server.social_platform_server.services.comment;

import com.server.social_platform_server.models.comment.Comment;
import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.comment.CommentRepository;
import com.server.social_platform_server.repositories.post.PostRepository;
import com.server.social_platform_server.repositories.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable){
        return commentRepository.findCommentsByPostId(postId, pageable);
    }
    public Page<Comment> getCommentsByUserId(Long userId, Pageable pageable){
        return commentRepository.findCommentsByUserId(userId, pageable);
    }

    public Comment saveComment(String text, Long userId, Long postId){//also update if id already existent
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User id is null or does not exist(commentservice): " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post id is null or does not exist(commentservice): " + postId));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        return this.commentRepository.save(comment);
    }
    public void deleteCommentById(Long commentId){
        commentRepository.deleteById(commentId);
    }
    public Comment getCommentById(Long commentId){
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment with given id not found (service): " + commentId));
    }
}
