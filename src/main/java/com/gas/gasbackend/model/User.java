package com.gas.gasbackend.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
@Schema(description = "Represents a user in the Grab a Slice platform")
public class User {

    @Id
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

    @ManyToMany
    @JoinTable(
            name = "app_user_skills",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id")
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

    // Constructors
    public User() {
        this.skills = new HashSet<>();
        this.projects = new HashSet<>();
    }

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.skills = new HashSet<>();
        this.projects = new HashSet<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    // Utility methods
    public void addSkill(Skill... skills) {
        this.skills.addAll(Arrays.asList(skills));
    }

    public void addProject(Project... projects) {
        this.projects.addAll(Arrays.asList(projects));
    }
}
