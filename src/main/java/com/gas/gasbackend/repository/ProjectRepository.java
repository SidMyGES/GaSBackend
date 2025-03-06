package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, String> {
    @Query("SELECT p FROM Project p JOIN p.collaborators c WHERE c.id = :userId")
    List<Project> findProjectsByCollaboratorId(@Param("userId") String userId);
}
