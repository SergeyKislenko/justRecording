package com.recording.controllers;

import com.recording.core.model.User;
import com.recording.core.service.DBServiceUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {
    private final DBServiceUser userService;

    public UserController(DBServiceUser userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(HttpServletResponse response, @RequestBody User user) throws IOException {
        userService.saveUser(user);
        response.sendRedirect("/orders");
    }


}
