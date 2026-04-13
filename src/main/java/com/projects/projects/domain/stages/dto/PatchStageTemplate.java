package com.projects.projects.domain.stages.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatchStageTemplate {
    @Size(max = 100)
    private String name;

    private String description;

    private String timeToFinish;

    private Boolean isDefault;
}
