package com.projects.projects.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projects.projects.exception.BusinessException;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.PatchTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
import com.projects.projects.tag.dto.TagResponse;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagResponse addTag(CreateTagRequest request) {
        return TagResponse.from(tagRepository.save(new Tag(request.getName())));
    }

    public Page<@NonNull TagResponse> queryTags(QueryTagRequest request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        Page<@NonNull Tag> queryTags = tagRepository.findByNameContainingIgnoreCase(request.getSearch(), pageable);

        return queryTags.map(TagResponse::from);
    }

    public TagResponse getTag(Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        return TagResponse.from(tag);
    }

    @Transactional
    public void deleteTag(Integer id, DeleteTagRequest request) {

        Tag deleteTag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        if (!request.getConfirmationName().equals(deleteTag.getName())) {
            throw new BusinessException("Nome incompatível.");
        }

        tagRepository.delete(deleteTag);
    }

    public TagResponse patchTag(Integer id, PatchTagRequest request) {
        Tag patchTag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        patchTag.setName(request.getName());

        return TagResponse.from(tagRepository.save(patchTag));
    }
}
