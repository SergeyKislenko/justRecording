package com.recording.controllers;

import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DBServiceUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final DBServiceUser userService;
    private final DBServiceOrder orderService;

    public MainController(DBServiceUser userService, DBServiceOrder orderService) {
        this.userService = userService;
        this.orderService = orderService;

    }


}
