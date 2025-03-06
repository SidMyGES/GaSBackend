package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    /**
     * Finds all top-level comments (comments without a parent).
     *
     * @return List of root comments
     */
    List<Comment> findByParentIsNull();

    /**
     * Finds all comments that have a specific parent ID.
     *
     * @param parentId Parent comment ID
     * @return List of replies
     */
    List<Comment> findByParent_Id(String parentId);

}
