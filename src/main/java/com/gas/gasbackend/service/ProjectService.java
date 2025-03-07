package com.gas.gasbackend.service;

import com.gas.gasbackend.dto.ProjectDTO;
import com.gas.gasbackend.model.*;
import com.gas.gasbackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectDTO::fromEntity).toList();
    }

    public ProjectDTO getProject(String id) {
        Project project = projectRepository.findById(id).orElse(null);
        return project != null ? ProjectDTO.fromEntity(project) : null;
    }

    public ProjectDTO createProject(ProjectDTO project) {
        return ProjectDTO.fromEntity(projectRepository.save(ProjectDTO.toEntity(project)));
    }

    public List<ProjectDTO> getProjectByUser(String userId) {
        return projectRepository.findProjectsByCollaboratorId(userId)
                .stream()
                .map(ProjectDTO::fromEntity)
                .toList();
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    public ProjectDTO updateProject(String id, ProjectDTO project) {
        Project projectToUpdate = projectRepository.findById(id).orElse(null);
        if (projectToUpdate != null) {
            projectToUpdate.setName(project.getName());
            projectToUpdate.setDescription(project.getDescription());
            return ProjectDTO.fromEntity(projectRepository.save(projectToUpdate));
        }
        return null;
    }

    public void likeProject(String id, String userId) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
//            project.getLikedBy().add(userId); // TODO avoir le userrepo pour trouver le user
            projectRepository.save(project);
        }
    }

    public void removeLike(String id, String userId) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getLikedBy().remove(userId);
            projectRepository.save(project);
        }
    }

    public void addSkill(String id, Skill skill) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getSkillsUsed().add(skill);
            projectRepository.save(project);
        }
    }

    public void removeSkill(String id, String skillId) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project != null) {
            project.getSkillsUsed().removeIf(skill -> skill.getId().equals(skillId));
            projectRepository.save(project);
        }
    }

    public void addCollaborator(String id, String collaborator) {
        Project project = projectRepository.findById(id).orElse(null);

        if (project != null) {
//            project.getCollaborators().add(collaborator); // TODO avoir le userrepo pour trouver le user
            projectRepository.save(project);
        }
    }

    public void removeCollaborator(String id, String collaborator) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getCollaborators().removeIf(user -> user.getId().equals(collaborator));
            projectRepository.save(project);
        }
    }

    public void addSlice(String id, Slice slice) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
//            project.getSlices().add(slice); //TODO avoir le slicerepo pour trouver le slice
            projectRepository.save(project);
        }
    }

    public void removeSlice(String id, String sliceId) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getSlices().removeIf(slice -> slice.getId().equals(sliceId));
            projectRepository.save(project);
        }
    }

    public void addComment(String id, Comment comment) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
//            project.getComments().add(comment); //TODO avoir le commentrepo pour trouver le comment
            projectRepository.save(project);
        }
    }

    public void removeComment(String id, String commentId) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getComments().removeIf(comment -> comment.getId().equals(commentId));
            projectRepository.save(project);
        }
    }

    public List<Comment> getComments(String id) { //TODO avoir le DTO
        Project project = projectRepository.findById(id).orElse(null);
        return project != null ? project.getComments().stream().toList() : null;
    }
}
