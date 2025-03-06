package com.gas.gasbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor // Constructeur sans argument nécessaire pour JPA
@Entity
@Table(name = "projects")
@Schema(description = "Represents a project (pizza) that a user has worked on.")
public class Project {

    @Id
    @JsonProperty("ID")
    @Schema(description = "Unique identifier for the project", accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "Name of the project", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Number of likes received on this project")
    private int likes;

    // Relation One-to-Many unidirectionnelle avec Comment.
    // La colonne "project_id" est ajoutée à la table "comments".
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Schema(description = "List of comments left on the project")
    private Set<Comment> comments = new HashSet<>();

    // Relation Many-to-Many avec Skill
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Schema(description = "Skills (toppings) used in this project", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Skill> skillsUsed = new HashSet<>();

    // Relation bidirectionnelle Many-to-Many avec User
    // Ce côté est propriétaire et définit la table de jointure "project_collaborators"
    @ManyToMany(mappedBy = "projects")
    @Schema(description = "Users who collaborated on this project", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<User> collaborators = new HashSet<>();

    // Relation One-to-Many unidirectionnelle avec Slice.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Schema(description = "Slices (mentorship offers/requests) associated with this project", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Slice> slices = new HashSet<>();

    // Constructeur avec argument (l'ID est généré automatiquement)
    public Project(final String name) {
        this.ID = UUID.randomUUID().toString();
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

    public void addSlice(final Slice slice) {
        this.slices.add(slice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
