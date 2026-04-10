package com.projects.projects.domain.memberProject.dto;

import com.projects.projects.domain.memberProject.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatchMembersDTO {
    private List<PatchMemberDTO> members;

    @Setter
    @Getter
    static public class PatchMemberDTO {
        Integer userId;
        Integer projectId;
        MemberRole role;
    }
}
