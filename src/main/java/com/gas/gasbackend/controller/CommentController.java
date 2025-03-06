package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        return commentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment)
    {
        String generatedId = UUID.randomUUID().toString();
        comment.setID(generatedId);
        Comment createdComment = commentRepository.save(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment updatedComment){
        Optional<Comment> commentopt = commentRepository.findById(id);
        if(!commentRepository.existsById(id))
            return ResponseEntity.notFound().build();
        commentRepository.deleteById(id);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable String id)
    {
        if(!commentRepository.existsById(id))
            return ResponseEntity.notFound().build();
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
