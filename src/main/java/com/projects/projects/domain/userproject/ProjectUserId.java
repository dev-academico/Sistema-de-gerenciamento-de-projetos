package com.projects.projects.domain.userproject;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ProjectUserId implements Serializable {
    private Integer projectId;
    private Integer userId;
}
