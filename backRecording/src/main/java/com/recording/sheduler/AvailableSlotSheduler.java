package com.recording.sheduler;

import com.recording.core.model.AvailableSlot;
import com.recording.core.service.DbServiceAvailableSlot;
import com.recording.core.utils.AvailableSlotUtil;
import com.recording.core.utils.DateTransform;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableSlotSheduler {
    private final AvailableSlotUtil availableSlotUtil;
    private final DbServiceAvailableSlot serviceAvailableSlot;

    public AvailableSlotSheduler(AvailableSlotUtil availableSlotUtil, DbServiceAvailableSlot serviceAvailableSlot) {
        this.availableSlotUtil = availableSlotUtil;
        this.serviceAvailableSlot = serviceAvailableSlot;
    }

    @Scheduled(cron = "${cron.schedule.refreshDay}")
    public void doRefreshForNewDay() {
        Optional<List<AvailableSlot>> availableSlots = serviceAvailableSlot.findAllByDay(DateTransform.getMinusDayCalendar(new Date(), 1));
        List<AvailableSlot> availableSlot = new ArrayList<>();
        availableSlots.ifPresent(availableSlot::addAll);
        for(AvailableSlot slot: availableSlot){
            slot.setActive(false);
            serviceAvailableSlot.saveSlot(slot);
        }
        availableSlotUtil.createSlotsForwardDay();
    }
}
