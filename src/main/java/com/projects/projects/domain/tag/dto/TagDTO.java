package com.projects.projects.domain.tag.dto;

import com.projects.projects.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDTO {
    private Integer id;
    private String name;

    public static TagDTO from(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }
}
