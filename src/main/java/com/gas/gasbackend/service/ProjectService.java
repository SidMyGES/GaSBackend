package com.gas.gasbackend.service;

import com.gas.gasbackend.dto.ProjectDTO;
import com.gas.gasbackend.model.Project;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.User;
import com.gas.gasbackend.repository.ProjectRepository;
import com.gas.gasbackend.repository.SliceRepository;
import com.gas.gasbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SliceRepository sliceRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(ProjectDTO::fromEntity).toList();
    }

    public ProjectDTO getProject(String id) {
        return projectRepository.findById(id)
                .map(ProjectDTO::fromEntity)
                .orElse(null);
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
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setName(project.getName());
                    existingProject.setDescription(project.getDescription());
                    return ProjectDTO.fromEntity(projectRepository.save(existingProject));
                })
                .orElse(null);
    }

    public void likeProject(String id, String userId) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(userId);

        if (projectOpt.isPresent() && userOpt.isPresent()) {
            Project project = projectOpt.get();
            project.getLikedBy().add(userOpt.get());
            projectRepository.save(project);
        }
    }

    public void removeLike(String id, String userId) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(userId);

        if (projectOpt.isPresent() && userOpt.isPresent()) {
            Project project = projectOpt.get();
            project.getLikedBy().remove(userOpt.get());
            projectRepository.save(project);
        }
    }

    public void addSkill(String id, Skill skill) {
        projectRepository.findById(id).ifPresent(project -> {
            project.getSkillsUsed().add(skill);
            projectRepository.save(project);
        });
    }

    public void removeSkill(String id, String skillId) {
        projectRepository.findById(id).ifPresent(project -> {
            project.getSkillsUsed().removeIf(skill -> skill.getId().equals(skillId));
            projectRepository.save(project);
        });
    }

    public void addCollaborator(String id, String collaboratorId) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(collaboratorId);

        if (projectOpt.isPresent() && userOpt.isPresent()) {
            Project project = projectOpt.get();
            project.getCollaborators().add(userOpt.get());
            projectRepository.save(project);
        }
    }

    public void removeCollaborator(String id, String collaboratorId) {
        projectRepository.findById(id).ifPresent(project -> {
            project.getCollaborators().removeIf(user -> user.getId().equals(collaboratorId));
            projectRepository.save(project);
        });
    }
}
