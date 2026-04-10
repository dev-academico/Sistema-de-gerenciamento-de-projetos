package com.projects.projects.services;

import com.projects.projects.domain.stages.StageTemplate;
import com.projects.projects.domain.stages.dto.CreateStageTemplate;
import com.projects.projects.domain.stages.dto.QueryStagesTemplatesDTO;
import com.projects.projects.domain.stages.dto.StageDTO;
import com.projects.projects.repositories.StageTemplateRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class StageTemplateService {
    private StageTemplateRepository stageTemplateRepository;
    @Autowired
    public StageTemplateService(StageTemplateRepository stageTemplateRepository) {
        this.stageTemplateRepository = stageTemplateRepository;
    }

    public StageDTO create(@NonNull CreateStageTemplate request) {
        StageTemplate stageTemplate = new StageTemplate();
        stageTemplate.setName(request.getName());
        stageTemplate.setDescription(request.getDescription());
        stageTemplate.setIsDefault(request.getIsDefault());
        stageTemplate.setDefaultOrder(1);

        if (request.getTimeToFinish() != null) {
            stageTemplate.setTimeToFinish(Duration.parse(request.getTimeToFinish().toString()));
        }

        StageTemplate stageTemplateCreated = stageTemplateRepository.save(stageTemplate);

        return StageDTO.from(stageTemplateCreated);
    }

    public Page<StageDTO> query(QueryStagesTemplatesDTO request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("name").ascending());

        return stageTemplateRepository.findAllByNameContainingIgnoreCase(
                request.getName(),
                pageable).map(StageDTO::from);
    }

    public ResponseEntity<Void> delete(@NonNull Integer id) {
        stageTemplateRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
