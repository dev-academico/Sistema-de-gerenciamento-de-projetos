package com.projects.projects.domain.user.dto;

import com.projects.projects.domain.user.UserRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número"
    )
    private String password;

    @NotNull
    private UserRole role;
}