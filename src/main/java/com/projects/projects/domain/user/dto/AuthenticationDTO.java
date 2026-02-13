package com.projects.projects.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDTO {
    @Email
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
