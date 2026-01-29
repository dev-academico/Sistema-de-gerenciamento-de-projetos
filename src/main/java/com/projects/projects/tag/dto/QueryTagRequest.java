package com.projects.projects.tag.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
public class QueryTagRequest {
    private String search = "";
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer page = 0;
    @Min(value = 0, message = "Valor deve ser positivo.")
    private Integer size = 5;
}
