package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "Represents a 'Slice' of a project, which is a sub-part showcasing specific skills")
public class Slice {

    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the slice",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "The name of the slice",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "The set of skills demonstrated in this slice",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final Set<Skill> skills;

    @Schema(description = "Comments left by users on this slice")
    private final Set<Comment> comments;

    @Schema(description = "Number of likes this slice has received")
    private int likes;

    public Slice(final String name) {
        this.name = name;
        this.skills = new HashSet<>();
        this.comments = new HashSet<>();
        this.likes = 0;
    }

    public void addSkills(final Skill... skills) {
        this.skills.addAll(Arrays.asList(skills));
    }

    public void addComments(final Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }
}

