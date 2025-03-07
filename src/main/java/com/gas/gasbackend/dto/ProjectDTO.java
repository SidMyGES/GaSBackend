package com.gas.gasbackend.dto;

import com.gas.gasbackend.dto.user.UserDTO;
import com.gas.gasbackend.model.Project;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Schema(description = "Data transfer object representing a project that a user has worked on.")
public class ProjectDTO {
    @Schema(description = "Unique identifier for the project")
    private String id;

    @Schema(description = "Name of the project")
    private String name;

    @Schema(description = "Description of the project")
    private String description;

    @Schema(description = "Number of likes")
    private int likes;

    @Schema(description = "Skills used in this project")
    private Set<String> skillsUsed;

    @Schema(description = "Users who collaborated on this project")
    private Set<UserDTO> collaborators;

    @Schema(description = "Slices associated with this project")
    private Set<SliceDTO> slices;

    @Schema(description = "Number of comments left on the project")
    private int commentCount;

    public ProjectDTO(String id, String name, String description, int likes, Set<String> skillsUsed, Set<UserDTO> collaborators, Set<SliceDTO> slices, int commentCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.likes = likes;
        this.skillsUsed = skillsUsed;
        this.collaborators = collaborators;
        this.slices = slices;
        this.commentCount = commentCount;
    }

    /**
     * Convertit un `Project` en `ProjectDTO`
     */
    public static ProjectDTO fromEntity(Project project) {
        int likes = project.getLikedBy().size();
        Set<String> skillsUsed = project.getSkillsUsed().stream().map(Skill::getName).collect(Collectors.toSet());
        Set<UserDTO> collaborators = project.getCollaborators().stream().map(User::mapUserToDto).collect(Collectors.toSet());
        Set<SliceDTO> slices = project.getSlices().stream().map(Slice::mapSliceToDto).collect(Collectors.toSet());
        int commentCount = project.getComments().size();

        return new ProjectDTO(project.getId(), project.getName(), project.getDescription(), likes, skillsUsed, collaborators, slices, commentCount);
    }

    /**
     * Convertit un `ProjectDTO` en `Project`
     */
    public static Project toEntity(ProjectDTO dto) {
        return new Project(dto.getName(), dto.getDescription());
    }
}
