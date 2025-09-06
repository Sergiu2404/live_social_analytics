package com.server.social_platform_server.controllers.reactions;

import com.server.social_platform_server.dto.reactions_dto.ReactionRequest;
import com.server.social_platform_server.models.post.Post;
import com.server.social_platform_server.models.reactions.CommentsReactions;
import com.server.social_platform_server.models.reactions.PostsReactions;
import com.server.social_platform_server.services.comment.CommentService;
import com.server.social_platform_server.services.reactions.ReactionsService;
import com.server.social_platform_server.services.user_details.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reactions")
public class ReactionsController {
    private final ReactionsService reactionsService;
    private final CommentService commentService;
    private final UserService userService;

    public ReactionsController(ReactionsService reactionsService, CommentService commentService, UserService userService){
        this.reactionsService = reactionsService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/comments/user/{id}")
    public Page<CommentsReactions> getCommentsReactionsByUser(
            @RequestParam Long id,
            Pageable pageable
    ){
        return this.reactionsService.getCommentsReactionsForUserId(id, pageable);
    }
    @PostMapping("/comments")
    public CommentsReactions addReactionToComment(
            @RequestBody ReactionRequest request
    ){
        return reactionsService.addReactionToComment(request.getUserId(), request.getTargetId(), request.getReactionType());
    }
    @DeleteMapping("/comments")
    public void removeReactionFromComment(@RequestBody ReactionRequest request){
        this.reactionsService.removeReactionFromComment(request.getUserId(), request.getTargetId());
    }
    @PutMapping("/comments")
    public CommentsReactions updateCommentReaction(@RequestBody ReactionRequest request) {
        return reactionsService.updateCommentReaction(
                request.getUserId(),
                request.getTargetId(),
                request.getReactionType()
        );
    }

    @GetMapping("/comments/{id}")
    public Page<CommentsReactions> getCommentsReactionsByComment(
            @RequestParam Long id,
            Pageable pageable
    ){
        return this.reactionsService.getCommentsReactionsForCommentId(id, pageable);
    }




    @GetMapping("/posts/user/{id}")
    public Page<PostsReactions> getPostsReactionsByUser(
            @RequestParam Long id,
            Pageable pageable
    ){
        return this.reactionsService.getPostsReactionsForUserId(id, pageable);
    }
    @PostMapping("/posts")
    public PostsReactions addReactionToPost(
            @RequestBody ReactionRequest request
    ){
        return this.reactionsService.addReactionToPost(request.getUserId(), request.getTargetId(), request.getReactionType());
    }
    @DeleteMapping("/posts")
    public void removeReactionFromPost(@RequestBody ReactionRequest request){
        this.reactionsService.removeReactionFromPost(request.getUserId(), request.getTargetId());
    }
    @PutMapping("/posts")
    public PostsReactions updatePostReaction(@RequestBody ReactionRequest request) {
        return reactionsService.updatePostReaction(
                request.getUserId(),
                request.getTargetId(),
                request.getReactionType()
        );
    }

    @GetMapping("/posts/{id}")
    public Page<PostsReactions> getPostsReactionsByPost(
            @RequestParam Long id,
            Pageable pageable
    ){
        return this.reactionsService.getPostsReactionsForPostId(id, pageable);
    }
}
