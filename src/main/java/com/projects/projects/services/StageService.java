package com.projects.projects.services;

import com.projects.projects.domain.stages.Stage;
import com.projects.projects.domain.stages.dto.CreateStageDTO;
import com.projects.projects.domain.stages.dto.StageDTO;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.repositories.ProjectRepository;
import com.projects.projects.repositories.StageRepository;
import com.projects.projects.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageService {
    final private StageRepository stageRepository;
    final private ProjectRepository projectRepository;
    @Autowired
    public StageService(StageRepository stageRepository,  ProjectRepository projectRepository,   UserRepository userRepository) {
        this.stageRepository = stageRepository;
        this.projectRepository = projectRepository;
    }

    public StageDTO registerStage(@NonNull CreateStageDTO dto, @NonNull Integer projectId, @NonNull Integer userId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        Stage stage = new Stage();

        stage.setStageStatus(dto.getStatus());
        stage.setDueDate(dto.getDueDate());
        stage.setName(dto.getName());
        stage.setDescription(dto.getDescription());
        stage.setProjectId(projectId);
        stage.setLastUpdatedBy(userId);

        return StageDTO.from(stageRepository.save(stage));
    }
}
