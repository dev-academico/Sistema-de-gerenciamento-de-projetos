package com.projects.projects.project;


import com.projects.projects.project.dto.CreateProjectRequest;
import com.projects.projects.project.dto.ProjectResponse;
import com.projects.projects.project.dto.QueryProjectRequest;
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
    public ResponseEntity<@NonNull ProjectResponse> save(@Valid @RequestBody CreateProjectRequest request) {
        return  new ResponseEntity<>(projectService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull ProjectResponse> query(@Valid @ModelAttribute QueryProjectRequest request) {
        return projectService.query(request);
    }
}
