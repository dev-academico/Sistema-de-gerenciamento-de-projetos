package com.projects.projects.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String>, JpaSpecificationExecutor<Project> {
    Project deleteById(Integer id);
    Optional<Project> findByName(String name);
}
