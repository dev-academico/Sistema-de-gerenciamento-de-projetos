package com.projects.projects.tag;

import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.PatchTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.Optional;

@Component
public class TagService  {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag addTag(CreateTagRequest request) {

        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("Nome pode ter no máximo 50 caracteres.");
        }

        Tag newTag = new Tag();
        newTag.setName(request.getName());

        return tagRepository.save(newTag);
    }

    public List<Tag> listTags() {
        return tagRepository.findAll();
    }

    public Page<Tag> queryTags(QueryTagRequest queryTagRequest) {
        PageRequest pageable = PageRequest.of(
                0,
                5,
                Sort.by("name").ascending()
        );

        return tagRepository.findByNamePage(queryTagRequest.getSearchName(), pageable);
    }

    @Transactional
    public void deleteTag(String id, DeleteTagRequest request) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada."));

        if (!request.getConfirmationName().equals(tag.getName())) {
            throw new IllegalArgumentException("Confirmação inválida.");
        }

        tagRepository.delete(tag);
    }

    public Tag patchTag(String id, PatchTagRequest request) {
        Tag patchTag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada."));

        patchTag.setName(request.getName());

        return tagRepository.save(patchTag);
    }
}
