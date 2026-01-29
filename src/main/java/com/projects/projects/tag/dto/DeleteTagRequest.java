package com.projects.projects.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class DeleteTagRequest {
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 50, min = 3, message = "Nome precisar ser maior que 3 e menor que 50 caracteres")
    private String confirmationName;
}
