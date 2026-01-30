package com.projects.projects.project.dto;

import com.projects.projects.project.Project;
import com.projects.projects.tag.Tag;
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
public class ProjectResponse {
    private Integer id;
    private String name;
    private String description;
    private Set<Tag> tags = new HashSet<>();

    public static ProjectResponse from(Project project) {
        ProjectResponse projectResponse = new ProjectResponse(project.getId(), project.getName(), project.getDescription(),  project.getTags());
        return projectResponse;
    }
}
