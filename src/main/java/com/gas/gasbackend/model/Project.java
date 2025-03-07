package com.gas.gasbackend.model;

import com.gas.gasbackend.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "projects")
@Schema(description = "Represents a project created by users.")
public class Project {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the project", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the project", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the project")
    private String description;

    @ManyToMany(mappedBy = "likedProjects")
    private Set<User> likedBy = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skillsUsed = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<User> collaborators = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<Slice> slices = new HashSet<>();

    public Project(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }

    public static ProjectDTO mapProjectToDto(final Project project) {
        return ProjectDTO.fromEntity(project);
    }
}
