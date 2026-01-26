package com.projects.projects.domain.tag;

import com.projects.projects.domain.tag.dto.CreateTagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagService  {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag AddTag(CreateTagRequest request) {

        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("Nome pode ter no máximo 50 caracteres");
        }

        Tag newTag = new Tag();
        newTag.setName(request.getName());

        return tagRepository.save(newTag);
    }

    public List<Tag> ListTags() {
        return tagRepository.findAll();
    }
}
