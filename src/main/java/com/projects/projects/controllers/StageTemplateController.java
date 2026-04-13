package com.projects.projects.controllers;

import com.projects.projects.domain.stages.dto.*;
import com.projects.projects.domain.user.User;
import com.projects.projects.services.StageTemplateService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stages")
public class StageTemplateController {
    private final StageTemplateService  stageTemplateService;

    @Autowired
    public StageTemplateController(StageTemplateService stageTemplateService) {
        this.stageTemplateService = stageTemplateService;
    }

    @PostMapping
    public ResponseEntity<StageDTO> create(@Valid @RequestBody CreateStageTemplate request) {
        StageDTO stage = stageTemplateService.create(request);

        return ResponseEntity.ok().body(stage);
    }

    @GetMapping
    public Page<@NonNull StageDTO> query(@Valid @ModelAttribute QueryStagesTemplatesDTO request) {
        return stageTemplateService.query(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stageTemplateService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StageDTO> update(@PathVariable Integer id, @Valid @RequestBody PatchStageTemplate request) {
        return ResponseEntity.ok().body(stageTemplateService.patch(id, request));
    }

    @PatchMapping("/reorder")
    public ResponseEntity<Void> update(@Valid @RequestBody List<Integer> request) {
        stageTemplateService.updateOrder(request);
        return ResponseEntity.ok().build();
    }
}
