package com.projects.projects.tag;

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

import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.PatchTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
import com.projects.projects.tag.dto.TagResponse;

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
    public ResponseEntity<@NonNull TagResponse> addTag(@Valid @RequestBody CreateTagRequest request) {
        return new ResponseEntity<>(tagService.addTag(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull TagResponse> queryTags(@Valid @ModelAttribute QueryTagRequest request) {
        return tagService.queryTags(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull TagResponse> getTag(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteTag(@Valid @ModelAttribute DeleteTagRequest request, @PathVariable Integer id) {
        tagService.deleteTag(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull TagResponse> updateTag(@PathVariable Integer id, @RequestBody PatchTagRequest request) {
        return new ResponseEntity<>(tagService.patchTag(id, request), HttpStatus.CREATED);
    }
}
