package com.projects.projects.controllers;

import com.projects.projects.domain.user.User;
import com.projects.projects.domain.user.dto.QueryUserDTO;
import com.projects.projects.domain.user.dto.UserDTO;
import com.projects.projects.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    UserService  userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDTO> query(@Valid @ModelAttribute QueryUserDTO request) {
        return userService.query(request);
    }

    @GetMapping("/my-user")
    public UserDTO getMyUser(@AuthenticationPrincipal User user) {
        return userService.getMyUser(user.getLogin());
    }
}
