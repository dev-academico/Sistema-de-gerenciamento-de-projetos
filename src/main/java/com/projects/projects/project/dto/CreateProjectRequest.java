package com.projects.projects.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequest {
    private String name;
    private String description;
    private List<String> tagIds;
}
