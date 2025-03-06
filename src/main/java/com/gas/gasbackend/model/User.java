package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "Represents a user in the Grab a Slice platform")
public class User {

    @Schema(description = "Unique identifier for the user",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "User's first name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "User's last name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Schema(description = "User's email address",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "User's password",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Set of skills the user has",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final Set<Skill> skills;

    @Schema(description = "Projects the user is working on or has created",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final Set<Project> projects;

    @Schema(description = "Number of likes received by the user")
    private int likes;

    @Schema(description = "Comments made under user's profile",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final Set<Comment> comments;

    public User(final String name, final String lastName, final String email, final String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.skills = new HashSet<>();
        this.projects = new HashSet<>();
        this.likes = 0;
        this.comments = new HashSet<>();
    }

    public void setSkills(final Skill... skills){
        this.skills.addAll(Arrays.asList(skills));
    }

    public void setProjects(final Project... projects) {
        this.projects.addAll(Arrays.asList(projects));
    }

    public void setComments(final Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }
}

