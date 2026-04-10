package com.projects.projects.domain.project.dto;

import com.projects.projects.domain.memberProject.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;

    @Size(max = 5, message = "O número máximo de tags é 5")
    private List<Integer> tagIds;

    @Size(max = 20, message = "O número máximo de membros é 20")
    private List<ProjectMemberCreateDTO> members;

    @Getter
    @Setter
    public static class ProjectMemberCreateDTO {
        private Integer userId;
        private MemberRole role;
    }
}
