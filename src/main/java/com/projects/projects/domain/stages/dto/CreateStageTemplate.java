package com.projects.projects.domain.stages.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateStageTemplate {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    private String name;

    private String description;

    private String timeToFinish;

    @NotNull(message = "Projeto associado é obrigatório")
    private Integer projectId;

    @NotNull(message = "Ativo/Desativado é obrigatório")
    private Boolean isDefault;
}
