package com.projects.projects.domain.stages.dto;

import com.projects.projects.domain.stages.StageStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CreateStageDTO {
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Size(min = 1, max = 500)
    private String description;

    @NotBlank
    private StageStatus status;

    @NotBlank
    private Date dueDate;

    @NotBlank
    private Integer defaultOrder;
}
