package com.recording.controllers;

import com.recording.core.model.AvailableSlot;
import com.recording.core.model.enums.OrderStatus;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.utils.AvailableSlotUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class OrderController {
    private final DBServiceOrder orderService;
    private final AvailableSlotUtil availableSlotUtil;

    public OrderController(DBServiceOrder orderService, AvailableSlotUtil availableSlotUtil) {
        this.orderService = orderService;
        this.availableSlotUtil = availableSlotUtil;
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

    @PostMapping(path = "/book")
    public void getDayForSlot(@RequestBody AvailableSlot slot, HttpServletResponse response) throws IOException {
        availableSlotUtil.createOrder(slot);
        response.sendRedirect("/orders");
    }
}
