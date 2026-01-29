package com.projects.projects.tag;

import com.projects.projects.tag.dto.*;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/api/v1/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<@NonNull TagResponse> addTag(@Valid @RequestBody CreateTagRequest request){

        return new ResponseEntity<>(tagService.addTag(request), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<@NonNull TagResponse> queryTags(@Valid @ModelAttribute QueryTagRequest request){
        return tagService.queryTags(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteTag(@PathVariable Integer id, @RequestBody DeleteTagRequest request) {
        tagService.deleteTag(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<@NonNull TagResponse> updateTag(@PathVariable Integer id, @RequestBody PatchTagRequest request) {

        return new ResponseEntity<>(tagService.patchTag(id, request), HttpStatus.CREATED);
    }
}
