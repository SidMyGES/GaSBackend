package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Retrieve all comments", description = "Fetches all stored comments")
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @Operation(summary = "Retrieve a comment by ID", description = "Fetches a single comment based on its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(
            @Parameter(description = "ID of the comment to fetch") @PathVariable String id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new comment", description = "Saves a new comment")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @Operation(summary = "Reply to a comment", description = "Creates a reply under an existing comment")
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<Comment> replyToComment(
            @Parameter(description = "ID of the parent comment") @PathVariable String parentId,
            @RequestBody Comment reply) {
        return commentService.replyToComment(parentId, reply)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a comment", description = "Removes a comment by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve replies", description = "Fetches replies under a specific comment")
    @GetMapping("/{parentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(
            @Parameter(description = "ID of the parent comment") @PathVariable final String parentId) {
        return ResponseEntity.ok(commentService.getReplies(parentId));
    }

    @Operation(summary = "Retrieve top-level comments", description = "Fetches all comments without a parent")
    @GetMapping("/top-level")
    public ResponseEntity<List<Comment>> getTopLevelComments() {
        return ResponseEntity.ok(commentService.getTopLevelComments());
    }
}
