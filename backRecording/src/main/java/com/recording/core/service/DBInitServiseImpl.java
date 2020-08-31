package com.recording.core.service;

import com.recording.core.model.Role;
import com.recording.core.model.User;

import java.util.Collections;

public class DBInitServiseImpl implements DBInitServise {
    private final DBServiceUser userService;

    public DBInitServiseImpl(DBServiceUser dbServiceUser) {
        this.userService = dbServiceUser;
    }

    @Override
    public void initUserDb() {
        userService.saveUser(createUser("admin", "admin", "ROLE_ADMIN"));
        userService.saveUser(createUser("user", "user", "ROLE_USER"));
    }

    private User createUser(String userName, String userPassword, String userRole) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassword);
        user.setRoles(Collections.singleton(new Role(userRole)));
        return user;
    }
}
