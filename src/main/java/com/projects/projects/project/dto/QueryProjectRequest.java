package com.projects.projects.project.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryProjectRequest {
    private String name = "";
    private String description = "";
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer page = 0;
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer size = 15;
}
