package com.recording.controllers;

import com.recording.core.model.AvailableSlot;
import com.recording.core.service.DbServiceAvailableSlot;
import com.recording.core.utils.AvailableSlotUtil;
import com.recording.core.utils.DateTransform;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("${route.api}")
public class AvailableSlotController {
    private final DbServiceAvailableSlot availableSlot;
    private final AvailableSlotUtil availableSlotUtil;

    public AvailableSlotController(DbServiceAvailableSlot availableSlot, AvailableSlotUtil availableSlotUtil) {
        this.availableSlot = availableSlot;
        this.availableSlotUtil = availableSlotUtil;
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
    public void getDayForSlot(@RequestBody AvailableSlot slot){
        availableSlotUtil.createOrder(slot);
    }
}
