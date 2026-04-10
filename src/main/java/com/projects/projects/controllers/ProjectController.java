package com.projects.projects.controllers;

import com.projects.projects.domain.memberProject.dto.PatchMembersDTO;
import com.projects.projects.domain.memberProject.dto.RemoveMembersDTO;
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
    public ResponseEntity<@NonNull ProjectDTO> create(@Valid @RequestBody CreateProjectDTO request, @AuthenticationPrincipal User owner) {
        return  new ResponseEntity<>(projectService.create(request, owner), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull ProjectWithoutMembersDTO> query(@Valid @ModelAttribute QueryProjectDTO request, @AuthenticationPrincipal User user) {
        return projectService.query(request, user);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        return projectService.get(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> delete(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        projectService.delete(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull ProjectWithoutMembersDTO> patch(@RequestBody PatchProjectDTO request, @PathVariable Integer id,  @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(projectService.patch(id, request, user), HttpStatus.OK);
    }

    @PatchMapping("/{id}/members")
    public ResponseEntity<Void> patchMembers(@Valid @RequestBody PatchMembersDTO request, @PathVariable Integer id,  @AuthenticationPrincipal User user) {
        projectService.patchMembers(id, request, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/members")
    public ResponseEntity<@NonNull Void> deleteMembers(@Valid @RequestBody RemoveMembersDTO request, @PathVariable Integer id, @AuthenticationPrincipal User user) {
        projectService.removeMembers(id, request, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
