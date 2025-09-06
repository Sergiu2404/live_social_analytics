package com.server.social_platform_server.services.reactions;

import com.server.social_platform_server.dto.reactions_dto.ReactionRequest;
import com.server.social_platform_server.models.comment.Comment;
import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.models.reactions.CommentsReactions;
import com.server.social_platform_server.models.reactions.PostsReactions;
import com.server.social_platform_server.models.reactions.ReactionType;
import com.server.social_platform_server.models.user.User;
import com.server.social_platform_server.repositories.comment.CommentRepository;
import com.server.social_platform_server.repositories.post.PostRepository;
import com.server.social_platform_server.repositories.reactions.CommentsReactionsRepository;
import com.server.social_platform_server.repositories.reactions.PostsReactionsRepository;
import com.server.social_platform_server.repositories.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class ReactionsService {
    private final CommentRepository commentRepository;
    private final PostsReactionsRepository postsReactionsRepository;
    private final CommentsReactionsRepository commentsReactionsRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ReactionsService(
            PostsReactionsRepository postsReactionsRepository,
            CommentsReactionsRepository commentsReactionsRepository,
            CommentRepository commentRepository,
            UserRepository userRepository,
            PostRepository postRepository
    ){
        this.commentsReactionsRepository = commentsReactionsRepository;
        this.postsReactionsRepository = postsReactionsRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Page<CommentsReactions> getCommentsReactionsForCommentId(Long commentId, Pageable pageable){
        return this.commentsReactionsRepository.findReactionsForCommentId(commentId, pageable);
    }
    public Page<CommentsReactions> getCommentsReactionsForUserId(Long userid, Pageable pageable){
        return this.commentsReactionsRepository.findReactionsByUserId(userid, pageable);
    }

    public Page<PostsReactions> getPostsReactionsForPostId(Long postId, Pageable pageable){
        return this.postsReactionsRepository.findReactionsForPostId(postId, pageable);
    }
    public Page<PostsReactions> getPostsReactionsForUserId(Long userId, Pageable pageable){
        return this.postsReactionsRepository.findReactionsByUserId(userId, pageable);
    }

    public CommentsReactions addReactionToComment(Long userId, Long commentId, ReactionType reactionType){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User id is null or does not exist(reactions service): " + userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment id is null or does not exist(reactions service): " + commentId));

        CommentsReactions commentsReactions = new CommentsReactions();
        commentsReactions.setUser(user);
        commentsReactions.setComment(comment);
        commentsReactions.setReactionType(reactionType);
        commentsReactions.setCreatedAt(LocalDateTime.now());

        return this.commentsReactionsRepository.save(commentsReactions);
    }
    public PostsReactions addReactionToPost(Long userId, Long postId, ReactionType reactionType){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User id is null or does not exist(reactions service): " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post id is null or does not exist(reactions service): " + postId));

        PostsReactions postsReactions = new PostsReactions();
        postsReactions.setUser(user);
        postsReactions.setPost(post);
        postsReactions.setReactionType(reactionType);
        postsReactions.setCreatedAt(LocalDateTime.now());

        return this.postsReactionsRepository.save(postsReactions);
    }

    public PostsReactions updatePostReaction(Long userId, Long postId, ReactionType reactionType){
        PostsReactions reaction = postsReactionsRepository
                .findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException(
                        "Reaction not found for user " + userId + " on post " + postId
                ));

        reaction.setReactionType(reactionType);
        return postsReactionsRepository.save(reaction);
    }
    public CommentsReactions updateCommentReaction(Long userId, Long commentId, ReactionType reactionType){
        CommentsReactions reaction = commentsReactionsRepository
                .findByUserIdAndCommentId(userId, commentId)
                .orElseThrow(() -> new RuntimeException(
                        "Reaction not found for user " + userId + " on comment " + commentId
                ));

        reaction.setReactionType(reactionType);
        return commentsReactionsRepository.save(reaction);
    }

    public void removeReactionFromComment(Long userId, Long commentId){
        this.commentsReactionsRepository.deleteByUserIdAndCommentId(userId, commentId);
    }
    public void removeReactionFromPost(Long userId, Long postId){
        this.postsReactionsRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
