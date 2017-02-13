package com.simple.hello.controller;

import com.google.common.base.Preconditions;
import com.simple.hello.domain.User;
import com.simple.hello.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User addNewUserWebForm(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(password);

        User user = User.builder()
                .password(password)
                .userName(userName)
                .build();

        return userService.addNewUser(user);
    }


    @PostMapping(value = "/api/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User addNewUserJson(@Valid @RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/users")
    public List<User> addNewUser() {
        return userService.findAll();
    }
}
