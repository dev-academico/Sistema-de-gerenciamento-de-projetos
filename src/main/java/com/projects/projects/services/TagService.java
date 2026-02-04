package com.projects.projects.services;

import com.projects.projects.domain.tag.Tag;
import com.projects.projects.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.projects.projects.exception.BusinessException;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.domain.tag.dto.CreateTagDTO;
import com.projects.projects.domain.tag.dto.DeleteTagDTO;
import com.projects.projects.domain.tag.dto.PatchTagDTO;
import com.projects.projects.domain.tag.dto.QueryTagDTO;
import com.projects.projects.domain.tag.dto.TagDTO;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagDTO addTag(CreateTagDTO request) {
        return TagDTO.from(tagRepository.save(new Tag(request.getName())));
    }

    public Page<@NonNull TagDTO> queryTags(QueryTagDTO request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        Page<@NonNull Tag> queryTags = tagRepository.findByNameContainingIgnoreCase(request.getSearch(), pageable);

        return queryTags.map(TagDTO::from);
    }

    public TagDTO getTag(Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        return TagDTO.from(tag);
    }

    @Transactional
    public void deleteTag(Integer id, DeleteTagDTO request) {

        Tag deleteTag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        if (!request.getConfirmationName().equals(deleteTag.getName())) {
            throw new BusinessException("Nome incompatível.");
        }

        tagRepository.delete(deleteTag);
    }

    public TagDTO patchTag(Integer id, PatchTagDTO request) {
        Tag patchTag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));

        patchTag.setName(request.getName());

        return TagDTO.from(tagRepository.save(patchTag));
    }
}
