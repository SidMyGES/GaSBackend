package com.gas.gasbackend.controller;

import com.gas.gasbackend.dto.ProjectDTO;
import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.model.Project;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.Slice;
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
    public ProjectDTO getProject(@RequestParam String id) {
        return projectService.getProject(id);
    }

    @GetMapping("/user/{id}")
    public List<ProjectDTO> getProjectByUser(@RequestParam String id) {
        return projectService.getProjectByUser(id);
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@RequestParam String id) {
        projectService.deleteProject(id);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@RequestParam String id,@RequestBody ProjectDTO project) {
        return projectService.updateProject(id, project);
    }

    @GetMapping("/{id}/like")
    public void likeProject(String id) {
        String userId = "1";
        projectService.likeProject(id, userId);
    }
    @DeleteMapping("/{id}/removeLike")
    public void removeLike(String id) {
        String userId = "1";
        projectService.removeLike(id, userId);
    }

    @PostMapping("/{id}/addSkill")
    public void addSkill(@RequestParam String id,@RequestBody Skill skill) { //todo avoir le DTO
        projectService.addSkill(id, skill);
    }

    @DeleteMapping("/{id}/removeSkill")
    public void removeSkill(@RequestParam String id,@RequestBody String skill) {
        projectService.removeSkill(id, skill);
    }

    @PostMapping("/{id}/addCollaborator")
    public void addCollaborator(@RequestParam String id,@RequestBody String collaborator) {
        projectService.addCollaborator(id, collaborator);
    }

    @DeleteMapping("/{id}/removeCollaborator")
    public void removeCollaborator(@RequestParam String id,@RequestBody String collaborator) {
        projectService.removeCollaborator(id, collaborator);
    }

    @PostMapping("/{id}/addSlice")
    public void addSlice(@RequestParam String id,@RequestBody Slice slice) { //TODO avoir le DTO
        projectService.addSlice(id, slice);
    }

    @DeleteMapping("/{id}/removeSlice")
    public void removeSlice(@RequestParam String id,@RequestBody String slice) {
        projectService.removeSlice(id, slice);
    }

    @PostMapping("/{id}/addComment")
    public void addComment(@RequestParam String id,@RequestBody Comment comment) { //TODO avoir le DTO
        projectService.addComment(id, comment);
    }

    @DeleteMapping("/{id}/removeComment")
    public void removeComment(@RequestParam String id,@RequestBody String comment) {
        projectService.removeComment(id, comment);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@RequestParam String id) {
        return projectService.getComments(id);
    }

}
