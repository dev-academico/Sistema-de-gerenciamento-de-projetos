package com.projects.projects.domain.tag.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryTagDTO {
    private String search = "";
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer page = 0;
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer size = 5;
}
