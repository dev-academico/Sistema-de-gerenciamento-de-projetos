package com.projects.projects.domain.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PatchTagDTO {
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 50, min = 3, message = "Nome precisar ser maior que 3 e menor que 50 caracteres")
    private String name;
}
