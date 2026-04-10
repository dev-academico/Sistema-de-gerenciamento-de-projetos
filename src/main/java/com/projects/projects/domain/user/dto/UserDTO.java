package com.projects.projects.domain.user.dto;

import com.projects.projects.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String login;

    static public UserDTO from (User user){
        return new UserDTO(user.getId(), user.getName(), user.getLogin());
    }
}
