package com.projects.projects.domain.memberProject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class MemberProjectId implements Serializable {
    private Integer projectId;
    private Integer userId;
}
