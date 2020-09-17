package com.recording.controllers;

import com.recording.core.model.AvailableSlot;
import com.recording.core.model.Order;
import com.recording.core.model.enums.OrderStatus;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DbServiceAvailableSlot;
import com.recording.core.utils.DateTransform;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AvailableSlotController {
    private final DbServiceAvailableSlot availableSlot;
    private final DBServiceOrder orderService;

    public AvailableSlotController(DbServiceAvailableSlot availableSlot, DBServiceOrder orderService) {
        this.availableSlot = availableSlot;
        this.orderService = orderService;
    }

    @GetMapping(path = "/getSlot")
    public List<AvailableSlot> getSlotByDate(@RequestParam("day") String day){
        Optional<List<AvailableSlot>> allAvailableSlot = availableSlot.findAllByDay(DateTransform.formatDate(day));
        List<AvailableSlot> availableSlot = new ArrayList<>();
        allAvailableSlot.ifPresent(availableSlot::addAll);
        return availableSlot;
    }

    @GetMapping(path = "/getDayForSlot")
    public List<AvailableSlot> getDayForSlot(){
        Optional<List<AvailableSlot>> allAvailableSlot = availableSlot.findAllDay();
        List<AvailableSlot> availableSlot = new ArrayList<>();
        allAvailableSlot.ifPresent(availableSlot::addAll);
        return availableSlot;
    }

    @PostMapping(path = "/book")
    public void getDayForSlot(@RequestBody Order order, HttpServletResponse response) throws IOException {
        order.setStatus(OrderStatus.REGISTRATION.getCode());
        Optional<AvailableSlot> slotOptional = availableSlot.findById(order.getSlotId());
        if(slotOptional.isPresent()){
            AvailableSlot slot = slotOptional.get();
            slot.setActive(false);
            availableSlot.saveSlot(slot);
        }
        orderService.saveOrder(order);
        response.sendRedirect("/orders");
    }
}
