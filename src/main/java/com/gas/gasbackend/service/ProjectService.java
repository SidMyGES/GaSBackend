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
        return ProjectDTO.fromEntity(projectRepository.findById(id).orElse(null));
    }

    public ProjectDTO createProject(Project project) {
        return ProjectDTO.fromEntity(projectRepository.save(project));
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    public ProjectDTO updateProject(String id, Project project) {
        project.setID(id);
        return ProjectDTO.fromEntity(projectRepository.save(project));
    }

    public void likeProject(String id, String userId) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            project.getLikedBy().add(userId);
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
            project.getSkillsUsed().removeIf(skill -> skill.getID().equals(skillId));
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
            project.getCollaborators().removeIf(user -> user.getID().equals(collaborator));
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
            project.getSlices().removeIf(slice -> slice.getID().equals(sliceId));
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
            project.getComments().removeIf(comment -> comment.getID().equals(commentId));
            projectRepository.save(project);
        }
    }

    public List<Comment> getComments(String id) { //TODO avoir le DTO
        Project project = projectRepository.findById(id).orElse(null);
        return project != null ? project.getComments().stream().toList() : null;
    }
}
