package com.recording.core.service.impl;

import com.recording.core.model.Order;
import com.recording.core.model.OrderStatus;
import com.recording.core.model.Role;
import com.recording.core.model.User;
import com.recording.core.service.DBInitServise;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DBServiceUser;

import java.util.Collections;
import java.util.Date;

public class DbInitServiseImpl implements DBInitServise {
    private final DBServiceUser userService;
    private final DBServiceOrder orderService;

    public DbInitServiseImpl(DBServiceUser dbServiceUser, DBServiceOrder orderService) {
        this.userService = dbServiceUser;
        this.orderService = orderService;
    }

    @Override
    public void initDb() {
        userService.saveUser(createUser("admin", "admin", "ROLE_ADMIN"));
        userService.saveUser(createUser("user", "user", "ROLE_USER"));
        orderService.saveOrder(createOrder(1L));
        orderService.saveOrder(createOrder(2L));
    }

    private User createUser(String userName, String userPassword, String userRole) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassword);
        user.setRoles(Collections.singleton(new Role(userRole)));
        return user;
    }

    private Order createOrder(long orderId) {
        Order order = new Order();
        order.setUserId(orderId);
        order.setStartPeriod(new Date());
        order.setEndPeriod(new Date());
        order.setStatus(OrderStatus.REGISTRATION.getDescription());
        return order;
    }
}
