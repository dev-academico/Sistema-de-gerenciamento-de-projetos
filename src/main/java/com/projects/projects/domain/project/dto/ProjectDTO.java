package com.projects.projects.domain.project.dto;

import com.projects.projects.domain.project.Project;
import com.projects.projects.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private Set<Tag> tags = new HashSet<>();

    public static ProjectDTO from(Project project) {
        ProjectDTO projectResponse = new ProjectDTO(project.getId(), project.getName(), project.getDescription(),  project.getTags());
        return projectResponse;
    }
}
