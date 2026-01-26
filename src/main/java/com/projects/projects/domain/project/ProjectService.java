package com.projects.projects.domain.project;

import com.projects.projects.domain.project.dto.CreateProjectRequest;
import com.projects.projects.domain.tag.Tag;
import com.projects.projects.domain.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Project> ListProjects() {
        return projectRepository.findAll();
    }

    public Project AddProject(CreateProjectRequest request) {
        Project newProject = new Project();

        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        if (tags.size() != request.getTagIds().size()) {
            throw new RuntimeException("One or more tag does not exist!");
        }

        newProject.getTags().addAll(tags);

        return projectRepository.save(newProject);
    }
}
