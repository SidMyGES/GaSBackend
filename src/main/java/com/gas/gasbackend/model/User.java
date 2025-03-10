package com.gas.gasbackend.model;

import com.gas.gasbackend.dto.user.UserCreateDTO;
import com.gas.gasbackend.dto.user.UserDTO;
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
    @Schema(description = "Unique identifier for the user", accessMode = Schema.AccessMode.READ_ONLY)
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
            name = "app_user_skills",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Set of skills the user has", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "app_user_projects",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Schema(description = "Projects the user is working on or has created", accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_liked_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Schema(description = "Projects liked by the user")
    private Set<Project> likedProjects = new HashSet<>();

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

    public void addLikedProject(Project project) {
        this.likedProjects.add(project);
        project.getLikedBy().add(this);
    }

    public void removeLikedProject(Project project) {
        this.likedProjects.remove(project);
        project.getLikedBy().remove(this);
    }

    public static UserDTO mapUserToDto(final User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    public static User mapDtoToUser(final UserCreateDTO userDTO) {
        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                ""
        );
    }
}
