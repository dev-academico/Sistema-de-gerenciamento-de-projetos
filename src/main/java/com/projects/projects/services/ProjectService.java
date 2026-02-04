package com.projects.projects.services;

import com.projects.projects.domain.project.Project;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.domain.project.dto.CreateProjectDTO;
import com.projects.projects.domain.project.dto.PatchProjectDTO;
import com.projects.projects.domain.project.dto.ProjectDTO;
import com.projects.projects.domain.project.dto.QueryProjectDTO;
import com.projects.projects.domain.tag.Tag;
import com.projects.projects.repositories.ProjectRepository;
import com.projects.projects.repositories.TagRepository;
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

    public Page<@NonNull ProjectDTO> query(QueryProjectDTO request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        Page<@NonNull Project> projects = projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(request.getName(), request.getDescription(), pageable);

        return projects.map(ProjectDTO::from);
    }

    public ProjectDTO create(CreateProjectDTO request) {
        Project newProject = new Project();

        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        if (tags.size() != request.getTagIds().size()) {
            throw new ResourceNotFoundException("Uma ou mais tags não existem!");
        }

        newProject.getTags().addAll(tags);

        return ProjectDTO.from(projectRepository.save(newProject));
    }

    public void delete(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }
        projectRepository.deleteById(id);
    }

    public ProjectDTO patch(Integer id, PatchProjectDTO request) {
        if(!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }
        Project project = projectRepository.findById(id).get();

        if (request.getName() != null) {
            project.setName(request.getName());
        }

        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }

        if (request.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());

            if (tags.size() != request.getTagIds().size()) {
                throw new ResourceNotFoundException("Uma ou mais tags não existem!");
            }

            project.getTags().clear();      // remove as antigas
            project.getTags().addAll(tags); // adiciona as novas
        }

        return ProjectDTO.from(projectRepository.save(project));
    }
}
