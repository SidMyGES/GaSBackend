package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "Represents user comments left under: other users, projects, or slices")
public class Comment {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the comment", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Schema(description = "Parent comment, null if top-level comment")
    private Comment parent;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    @Schema(description = "The user who wrote the comment", requiredMode = Schema.RequiredMode.REQUIRED)
    private User writer;

    @Schema(description = "The content of the comment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @SuppressWarnings("unused")
    public Comment(final User writer, final Comment parent, final String content) {
        this.id = UUID.randomUUID().toString();
        this.writer = writer;
        this.parent = parent;
        this.content = content;
    }

    @SuppressWarnings("unused")
    public String getParentId() {
        return parent != null ? parent.getId() : null;
    }
}

