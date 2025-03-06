package com.gas.gasbackend.dto;

import com.gas.gasbackend.model.Project;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Schema(description = "Data transfer object representing a project (pizza) that a user has worked on.")
public class ProjectDTO {
    @Schema(description = "Unique identifier for the project")
    private String ID;
    @Schema(description = "Name of the project")
    private String name;

    @Schema(description = "description of the project")
    private String description;

    @Schema(description = "number of likes")
    private int likes;

    @Schema(description = "Skills (toppings) used in this project")
    private Set<String> skillsUsed;

    @Schema(description = "Users who collaborated on this project")
    private Set<String> collaborators;

    @Schema(description = "Slices (mentorship offers/requests) associated with this project")
    private Set<String> slices;

    @Schema(description = "Number of comments left on the project")
    private int commentCount;

    public ProjectDTO(String ID, String name, String description, int likedBy, Set<String> skillsUsed, Set<String> collaborators, Set<String> slices, int commentCount) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.likes = likedBy;
        this.skillsUsed = skillsUsed;
        this.collaborators = collaborators;
        this.slices = slices;
        this.commentCount = commentCount;
    }

    // Optionally, you could add a method to convert a Project to ProjectDTO if needed
    public static ProjectDTO fromEntity(Project project) {
        int likedBy = project.getLikedBy().size();
        Set<String> skillsUsed = project.getSkillsUsed().stream().map(Skill::getName).collect(Collectors.toSet());
        Set<String> collaborators = project.getCollaborators().stream().map(User::getName).collect(Collectors.toSet()); // TODO changer en DTO
        Set<String> slices = project.getSlices().stream().map(Slice::getID).collect(Collectors.toSet());
        int commentCount = project.getComments().size();
        return new ProjectDTO(project.getID(), project.getName(), project.getDescription(), likedBy, skillsUsed, collaborators, slices, commentCount);
    }
}
