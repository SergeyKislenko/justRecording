package com.recording.controllers;

import com.recording.core.model.enums.OrderStatus;
import com.recording.core.service.DBServiceOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class OrderController {
    private final DBServiceOrder orderService;

    public OrderController(DBServiceOrder orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/orderDone")
    public void addOrder(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        orderService.changeOrderStatus(Long.parseLong(id), OrderStatus.DONE.getCode());
        response.sendRedirect("/orders");
    }

    @GetMapping(path = "/orderCancel")
    public void orderCancel(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        orderService.changeOrderStatus(Long.parseLong(id), OrderStatus.CANCEL.getCode());
        response.sendRedirect("/orders");
    }


}
