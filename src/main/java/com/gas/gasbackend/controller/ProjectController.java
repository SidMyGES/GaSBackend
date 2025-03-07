package com.gas.gasbackend.controller;

import com.gas.gasbackend.dto.ProjectDTO;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable String id) {
        return projectService.getProject(id);
    }

    @GetMapping("/user/{userId}")
    public List<ProjectDTO> getProjectByUser(@PathVariable String userId) {
        return projectService.getProjectByUser(userId);
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable String id, @RequestBody ProjectDTO project) {
        return projectService.updateProject(id, project);
    }

    @PostMapping("/{id}/like/{userId}")
    public void likeProject(@PathVariable String id, @PathVariable String userId) {
        projectService.likeProject(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable String id, @PathVariable String userId) {
        projectService.removeLike(id, userId);
    }

    @PostMapping("/{id}/skill")
    public void addSkill(@PathVariable String id, @RequestBody Skill skill) {
        projectService.addSkill(id, skill);
    }

    @DeleteMapping("/{id}/skill/{skillId}")
    public void removeSkill(@PathVariable String id, @PathVariable String skillId) {
        projectService.removeSkill(id, skillId);
    }

    @PostMapping("/{id}/collaborator/{collaboratorId}")
    public void addCollaborator(@PathVariable String id, @PathVariable String collaboratorId) {
        projectService.addCollaborator(id, collaboratorId);
    }

    @DeleteMapping("/{id}/collaborator/{collaboratorId}")
    public void removeCollaborator(@PathVariable String id, @PathVariable String collaboratorId) {
        projectService.removeCollaborator(id, collaboratorId);
    }
}
