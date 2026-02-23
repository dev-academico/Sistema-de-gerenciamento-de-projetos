package com.projects.projects.domain.project.dto;

import com.projects.projects.domain.userproject.ProjectUserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectMemberCreateDTO {
    private Integer userId;
    private ProjectUserRole role;
}
