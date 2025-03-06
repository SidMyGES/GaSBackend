package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@Schema(description = "Represents a user in the Grab a Slice platform")
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the user",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "User's first name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "User's last name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "User's email address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "User's password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Comments made by the user", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Set of skills the user has", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Schema(description = "Projects the user is working on or has created", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Project> projects = new HashSet<>();

    @Schema(description = "Number of likes received by the user")
    private int likes;

    public User(final String name, final String lastName, final String email, final String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.likes = 0;
    }


    public void addSkills(Skill... skills) {
        this.skills.addAll(Arrays.asList(skills));
    }

    public void addProjects(final Project... projects) {
        this.projects.addAll(Arrays.asList(projects));
    }

    // Méthode qui gère la relation bidirectionnelle
    public void setProject(Project project) {
        this.projects.add(project);
        project.getCollaborators().add(this);
    }

    public void addComments(Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
