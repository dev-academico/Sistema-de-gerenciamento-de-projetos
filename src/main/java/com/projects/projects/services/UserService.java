package com.projects.projects.services;

import com.projects.projects.domain.user.User;
import com.projects.projects.domain.user.dto.QueryUserDTO;
import com.projects.projects.domain.user.dto.UserDTO;
import com.projects.projects.repositories.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDTO> query(QueryUserDTO request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.ASC, "name")
        );

        Page<User> userDTOPage = userRepository.findAll(pageable);

        return userDTOPage.map(UserDTO::from);
    }

    public UserDTO getMyUser(@Email @NotBlank String login) {
        return UserDTO.from(userRepository.findUserByLogin(login));
    }
}
