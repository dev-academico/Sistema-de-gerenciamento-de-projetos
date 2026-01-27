package com.projects.projects.tag;

import com.projects.projects.tag.dto.CreateTagRequest;
import com.projects.projects.tag.dto.DeleteTagRequest;
import com.projects.projects.tag.dto.QueryTagRequest;
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

    public Page<Tag> QueryTags(QueryTagRequest queryTagRequest) {
        PageRequest pageable = PageRequest.of(
                0,
                5,
                Sort.by("name").ascending()
        );

        return tagRepository.findByNamePage(queryTagRequest.getName(), pageable);
    }

    public void DeleteTag(DeleteTagRequest request) throws InvalidKeyException, IllegalArgumentException {
        Optional<Tag> deletedTag = tagRepository.findById(String.valueOf(request.getId()));
        if(deletedTag.isPresent() && request.getConfirmationName().equals(deletedTag.get().getName())){
            tagRepository.deleteById(request.getId());
        } else if (deletedTag.isEmpty()) {
            throw new InvalidKeyException("Tag não encontrada!");
        } else {
            throw new IllegalArgumentException("Nome inválido!");
        }

    }
}
