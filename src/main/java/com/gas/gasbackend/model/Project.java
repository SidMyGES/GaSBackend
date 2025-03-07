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
import java.util.*;

@Entity
@Data
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
    @Schema(description = "Number of likes received on this project")
    private int likes;

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
    // Relation bidirectionnelle Many-to-Many avec User
    // Ce côté est propriétaire et définit la table de jointure "project_collaborators"
    @ManyToMany(mappedBy = "projects")
    @Schema(description = "Users who collaborated on this project", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<User> collaborators = new HashSet<>();

    // Relation One-to-Many unidirectionnelle avec Slice.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Schema(description = "Slices associated with this project")
    private Set<Slice> slices = new HashSet<>();

    // Constructeur avec argument (l'ID est généré automatiquement)
    public Project(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.likes = 0;
    }

    // Méthodes utilitaires
    public void addComment(final Comment comment) {
        this.comments.add(comment);
    }

    public void addSkillUsed(final Skill skill) {
        this.skillsUsed.add(skill);
    }

    // Méthode qui gère la relation bidirectionnelle
    public void addCollaborator(final User user) {
        this.collaborators.add(user);
        user.getProjects().add(this);
    }

//    public void addSlice(final Slice slice) {
//        this.slices.add(slice);
//    }
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