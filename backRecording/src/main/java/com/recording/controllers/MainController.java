package com.recording.controllers;

import com.recording.core.model.User;
import com.recording.core.service.DBServiceUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    private final DBServiceUser userService;

    public MainController(DBServiceUser userService) {
        this.userService = userService;

    }

    @RequestMapping("/admin")
    public List<User> adminContext() {
        Optional<List<User>> allUsers = userService.findAll();
        List<User> users = new ArrayList<>();
        allUsers.ifPresent(users::addAll);
        return users;
    }

    @RequestMapping("/user")
    public List<User> userContext() {
        Optional<List<User>> allUsers = userService.findAll();
        List<User> users = new ArrayList<>();
        allUsers.ifPresent(users::addAll);
        return users;
    }

    @RequestMapping("/")
    public String index() {
        return "main box";
    }
}
