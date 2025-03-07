package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
//@EqualsAndHashCode(exclude = {"likedBy", "comments", "skillsUsed", "collaborators", "slices"})
@Table(name = "projects")
@NoArgsConstructor
@Schema(description = "Represents a project created by users.")
public class Project {

    @Id
    @Schema(description = "Unique identifier for the project", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the project", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the project")
    private String description;

    @Schema(description = "Number of likes received by the project")
    @JoinTable(
            name = "project_likes",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ManyToMany
    private Set<User> likedBy = new HashSet<>();

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

    public Project(final String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.getId());
    }

}