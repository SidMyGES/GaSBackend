package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "Represents a project created by users.")
public class Project {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the project", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the project", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Number of likes received by the project")
    private int likes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Schema(description = "Comments left by users on this project")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Skills demonstrated in this project", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Skill> skillsUsed = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "project_collaborators",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Schema(description = "Users who collaborated on this project", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<User> collaborators = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @Schema(description = "Slices associated with this project")
    private Set<Slice> slices = new HashSet<>();

    public Project(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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
}