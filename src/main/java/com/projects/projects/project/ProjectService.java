package com.projects.projects.project;

import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.project.dto.CreateProjectRequest;
import com.projects.projects.project.dto.ProjectResponse;
import com.projects.projects.project.dto.QueryProjectRequest;
import com.projects.projects.tag.Tag;
import com.projects.projects.tag.TagRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Autowired
    private ProjectService(ProjectRepository projectRepository, TagRepository tagRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;

    }

    public Page<@NonNull ProjectResponse> query(QueryProjectRequest request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        Page<@NonNull Project> projects = projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(request.getName(), request.getDescription(), pageable);

        return projects.map(ProjectResponse::from);
    }

    public ProjectResponse create(CreateProjectRequest request) {
        Project newProject = new Project();

        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        if (tags.size() != request.getTagIds().size()) {
            throw new ResourceNotFoundException("One or more tag does not exist!");
        }

        newProject.getTags().addAll(tags);

        return ProjectResponse.from(projectRepository.save(newProject));
    }
}
