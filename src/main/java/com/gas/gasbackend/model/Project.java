package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "Represents a project (pizza) that a user has worked on.")
public class Project {

    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the project",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "Name of the project",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Number of likes received on this project")
    private int likes;

    @Schema(description = "List of comments left on the project")
    private final Set<Comment> comments;

    @Schema(description = "Skills (toppings) used in this project",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final Set<Skill> skillsUsed;

    @Schema(description = "Users who collaborated on this project",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final Set<User> collaborators;

    @Schema(description = "Slices (mentorship offers/requests) associated with this project",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final Set<Slice> slices;

    public Project(final String name) {
        this.name = name;
        this.comments = new HashSet<>();
        this.skillsUsed = new HashSet<>();
        this.collaborators = new HashSet<>();
        this.slices = new HashSet<>();
    }

    public void addComments(final Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    public void addSkillsUsed(final Skill... skills) {
        this.skillsUsed.addAll(Arrays.asList(skills));
    }

    public void addCollaborators(final User... collaborators){
        this.collaborators.addAll(Arrays.asList(collaborators));
    }

    public void addSlices(final Slice... slices) {
        this.slices.addAll(Arrays.asList(slices));
    }
}
