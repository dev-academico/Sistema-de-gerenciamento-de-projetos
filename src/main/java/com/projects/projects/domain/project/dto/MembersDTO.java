package com.projects.projects.domain.project.dto;

import com.projects.projects.domain.userproject.ProjectUserRole;
import com.projects.projects.domain.userproject.UserProject;
import lombok.*;
import org.springframework.boot.json.JsonWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MembersDTO {
    private String email;
    private ProjectUserRole projectUserRole;
    static public MembersDTO from(UserProject member){
        return new MembersDTO(member.getUser().getLogin(), member.getRole());
    }
    static public Set<MembersDTO> fromList(List<UserProject> members) {
        Set<MembersDTO> listMembers = new HashSet<>();

        for (UserProject member : members) {
            listMembers.add(from(member));
        }

        return listMembers;
    }
}
