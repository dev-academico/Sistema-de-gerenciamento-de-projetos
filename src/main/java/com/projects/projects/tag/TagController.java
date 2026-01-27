package com.projects.projects.tag;

import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
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

    @PostMapping("/query")
    public Page<Tag> QueryTags(@RequestBody QueryTagRequest request) {
        return tagService.QueryTags(request);
    }

    @DeleteMapping
    public ResponseEntity<Object> DeleteTag(@RequestBody DeleteTagRequest request) throws InvalidKeyException, IllegalArgumentException {
        tagService.DeleteTag(request);

        return ResponseEntity.noContent().build();
    }
}
