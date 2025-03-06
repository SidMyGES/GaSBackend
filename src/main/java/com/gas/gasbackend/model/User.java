package com.gas.gasbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor // Constructeur sans argument nécessaire pour JPA
@Entity
@Table(name = "users")
@Schema(description = "Represents a user in the Grab a Slice platform")
public class User {

    @Id
    @JsonProperty("ID")
    @Schema(description = "Unique identifier for the user", accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "User's first name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "User's last name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "User's email address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "User's password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    // Relation Many-to-Many avec Skill
    @ManyToMany
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Set of skills the user has", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    // Relation bidirectionnelle Many-to-Many avec Project
    // Ici, le côté inverse est défini par "mappedBy" qui fait référence au champ "collaborators" de la classe Project
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Schema(description = "Projects in which the user participates", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Project> projects = new HashSet<>();

    @Schema(description = "Number of likes received by the user")
    private int likes;

    // Relation One-to-Many avec Comment (le champ "writer" dans Comment est le propriétaire)
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Comments made by the user", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Comment> comments = new HashSet<>();

    // Constructeur avec arguments
    public User(String name, String lastName, String email, String password) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Méthodes utilitaires
    public void addSkills(Skill... skills) {
        this.skills.addAll(Arrays.asList(skills));
    }

    // Méthode qui gère la relation bidirectionnelle
    public void addProject(Project project) {
        this.projects.add(project);
        project.getCollaborators().add(this);
    }

    public void addComments(Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
