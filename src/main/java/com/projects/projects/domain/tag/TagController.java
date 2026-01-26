package com.projects.projects.domain.tag;

import com.projects.projects.domain.tag.dto.CreateTagRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Tag> AddTag(@RequestBody CreateTagRequest request){


        Tag createdTag = tagService.AddTag(request);

        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tag> ListTags() {
        return tagService.ListTags();
    }
}
