package com.projects.projects.controllers;

import com.projects.projects.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.projects.domain.tag.dto.CreateTagDTO;
import com.projects.projects.domain.tag.dto.DeleteTagDTO;
import com.projects.projects.domain.tag.dto.PatchTagDTO;
import com.projects.projects.domain.tag.dto.QueryTagDTO;
import com.projects.projects.domain.tag.dto.TagDTO;

import jakarta.validation.Valid;
import lombok.NonNull;

@RestController()
@RequestMapping(path = "/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<@NonNull TagDTO> addTag(@Valid @RequestBody CreateTagDTO request) {
        return new ResponseEntity<>(tagService.addTag(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull TagDTO> queryTags(@Valid @ModelAttribute QueryTagDTO request) {
        return tagService.queryTags(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull TagDTO> getTag(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteTag(@Valid @ModelAttribute DeleteTagDTO request, @PathVariable Integer id) {
        tagService.deleteTag(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull TagDTO> updateTag(@PathVariable Integer id, @RequestBody PatchTagDTO request) {
        return new ResponseEntity<>(tagService.patchTag(id, request), HttpStatus.OK);
    }
}
