package com.projects.projects.services;

import com.projects.projects.domain.stages.StageTemplate;
import com.projects.projects.domain.stages.dto.*;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.repositories.StageTemplateRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class StageTemplateService {
    private final StageTemplateRepository stageTemplateRepository;
    @Autowired
    public StageTemplateService(StageTemplateRepository stageTemplateRepository) {
        this.stageTemplateRepository = stageTemplateRepository;
    }

    public StageDTO create(@NonNull CreateStageTemplate request) {
        StageTemplate stageTemplate = new StageTemplate();
        stageTemplate.setName(request.getName());
        stageTemplate.setDescription(request.getDescription());
        stageTemplate.setIsDefault(request.getIsDefault());
        stageTemplate.setDefaultOrder((int) stageTemplateRepository.count());

        if (request.getTimeToFinish() != null) {
            stageTemplate.setTimeToFinish(Duration.parse(request.getTimeToFinish()));
        }

        StageTemplate stageTemplateCreated = stageTemplateRepository.save(stageTemplate);

        return StageDTO.from(stageTemplateCreated);
    }

    public Page<@NonNull StageDTO> query(QueryStagesTemplatesDTO request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("default_order").ascending());

        return stageTemplateRepository.findAllByNameContainingIgnoreCase(
                request.getName(),
                pageable).map(StageDTO::from);
    }

    public void delete(@NonNull Integer id) {
        stageTemplateRepository.deleteById(id);
    }

    public StageDTO patch(Integer id, PatchStageTemplate request) {
        Optional<StageTemplate> stage = stageTemplateRepository.findById(id);

        if(stage.isEmpty()) {
            throw new ResourceNotFoundException("Stage não encontrado");
        }

        if(request.getName() != null) {
            stage.get().setName(request.getName());
        }
        if(request.getDescription() != null) {
            stage.get().setDescription(request.getDescription());
        }
        if(request.getIsDefault() != null) {
            stage.get().setIsDefault(request.getIsDefault());
        }
        if (request.getTimeToFinish() != null) {
            stage.get().setTimeToFinish(Duration.parse(request.getTimeToFinish()));
        }

        return StageDTO.from(stageTemplateRepository.save(stage.get()));
    }

    @Transactional
    public void updateOrder(List<Integer> idsInOrder) {
        List<StageTemplate> stageTemplates = stageTemplateRepository.findAllById(idsInOrder);

        if(stageTemplates.size() != idsInOrder.size()) {
            throw new ResourceNotFoundException("Um ou mais stages não encontrados");
        }

        for (Integer id : idsInOrder) {
            stageTemplates.stream().filter(stage -> stage.getId().equals(id)).findFirst().ifPresent(stage -> stage.setDefaultOrder(idsInOrder.indexOf(id)));
        }

        stageTemplateRepository.saveAll(stageTemplates);
    }
}
