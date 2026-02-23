package com.projects.projects.controllers;

import com.projects.projects.domain.project.dto.*;
import com.projects.projects.domain.user.User;
import com.projects.projects.services.ProjectService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<@NonNull ProjectDTO> save(@Valid @RequestBody CreateProjectDTO request, @AuthenticationPrincipal User owner) {
        return  new ResponseEntity<>(projectService.create(request, owner), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull ProjectWithoutMembersDTO> query(@Valid @ModelAttribute QueryProjectDTO request) {
        return projectService.query(request);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Integer id) {
        return projectService.get(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> delete(@PathVariable Integer id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull ProjectWithoutMembersDTO> patch(@Valid @RequestBody PatchProjectDTO request, @PathVariable Integer id) {
        return new ResponseEntity<>(projectService.patch(id, request), HttpStatus.OK);
    }
}
