package com.projects.projects.domain.memberProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RemoveMembersDTO {
    private List<Integer> userIds;
}
