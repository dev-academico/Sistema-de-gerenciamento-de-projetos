package com.projects.projects.domain.memberProject.dto;

import com.projects.projects.domain.memberProject.MemberRole;
import com.projects.projects.domain.memberProject.MemberProject;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MembersDTO {
    private Integer id;
    private String email;
    private MemberRole role;

    static public MembersDTO from(MemberProject member) {
        return new MembersDTO(member.getUser().getId(), member.getUser().getLogin(), member.getRole());
    }

    static public Set<MembersDTO> fromList(List<MemberProject> members) {
        Set<MembersDTO> listMembers = new HashSet<>();

        for (MemberProject member : members) {
            listMembers.add(from(member));
        }

        return listMembers;
    }
}