package com.projects.projects.tag;

import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.PatchTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping(path = "tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> addTag(@RequestBody CreateTagRequest request){
        Tag createdTag = tagService.addTag(request);

        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tag> listTags() {
        return tagService.listTags();
    }

    @PostMapping("/query")
    public Page<Tag> queryTags(@RequestBody QueryTagRequest request) {
        return tagService.queryTags(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable String id, @RequestBody DeleteTagRequest request) {
        tagService.deleteTag(id, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable String id, @RequestBody PatchTagRequest request) {
        Tag tagPatched = tagService.patchTag(id, request);

        return new ResponseEntity<>(tagPatched, HttpStatus.CREATED);
    }
}
