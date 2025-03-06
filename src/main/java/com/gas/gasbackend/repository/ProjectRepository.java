package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, String> {
}
