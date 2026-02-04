package com.projects.projects.controllers;


import com.projects.projects.services.ProjectService;
import com.projects.projects.domain.project.dto.CreateProjectDTO;
import com.projects.projects.domain.project.dto.PatchProjectDTO;
import com.projects.projects.domain.project.dto.ProjectDTO;
import com.projects.projects.domain.project.dto.QueryProjectDTO;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<@NonNull ProjectDTO> save(@Valid @RequestBody CreateProjectDTO request) {
        return  new ResponseEntity<>(projectService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull ProjectDTO> query(@Valid @ModelAttribute QueryProjectDTO request) {
        return projectService.query(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> delete(@PathVariable Integer id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull ProjectDTO> patch(@Valid @RequestBody PatchProjectDTO request, @PathVariable Integer id) {
        return new ResponseEntity<>(projectService.patch(id, request), HttpStatus.OK);
    }
}
