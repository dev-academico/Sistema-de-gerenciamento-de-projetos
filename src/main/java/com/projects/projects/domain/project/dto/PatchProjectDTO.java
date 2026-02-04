package com.projects.projects.domain.project.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchProjectDTO {
    private String name;

    private String description;

    @Size(max = 5, message = "O número máximo de tags é 5")
    private List<Integer> tagIds;
}
