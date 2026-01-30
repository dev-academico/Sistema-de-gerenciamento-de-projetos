package com.projects.projects.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    Page<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
}
