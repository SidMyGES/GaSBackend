package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;


@Data
@Schema(description = "Represents user comments left under: other users, projects, or slices")
public class Comment {

    @Setter(AccessLevel.NONE)
    @Schema(description = "This field represents a unique identifier for a comment",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "if the comment is a reply, this field will contain the ID of the parent comment, else it would be empty",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentID;

    @Schema(description = "The user who wrote the comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final User writer;

    @Schema(description = "The content of the comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final String content;


    public Comment(final User writer, final String parentID, final String content) {
        this.writer = writer;
        this.parentID = parentID;
        this.content = content;
    }
}
