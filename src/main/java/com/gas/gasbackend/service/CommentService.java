package com.gas.gasbackend.service;

import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service handling CRUD operations for comments.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Retrieves all existing comments.
     *
     * @return List of all comments
     */
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id the comment's unique identifier
     * @return Optional containing the comment if found
     */
    public Optional<Comment> getCommentById(final String id) {
        return commentRepository.findById(id);
    }

    /**
     * Creates and saves a new comment.
     *
     * @param comment the Comment object to create
     * @return the saved Comment object
     */
    public Comment createComment(final Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Creates a reply to an existing comment.
     *
     * @param parentId ID of the parent comment
     * @param reply    the reply Comment object
     * @return Optional containing the reply if parent exists
     */
    public Optional<Comment> replyToComment(final String parentId, final Comment reply) {
        return commentRepository.findById(parentId).map(parent -> {
            reply.setParent(parent);
            return commentRepository.save(reply);
        });
    }

    /**
     * Deletes a comment by ID.
     *
     * @param id the ID of the comment to delete
     */
    public void deleteComment(final String id) {
        commentRepository.deleteById(id);
    }

    /**
     * Retrieves all comments that are replies to a specific parent.
     *
     * @param parentId ID of the parent comment
     * @return List of replies
     */
    public List<Comment> getReplies(final String parentId) {
        return commentRepository.findByParent_Id(parentId);
    }

    /**
     * Retrieves all top-level comments (comments without a parent).
     *
     * @return List of top-level comments
     */
    public List<Comment> getTopLevelComments() {
        return commentRepository.findByParentIsNull();
    }
}
