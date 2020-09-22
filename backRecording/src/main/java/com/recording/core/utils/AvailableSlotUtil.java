package com.recording.core.utils;

import com.recording.core.model.AvailableSlot;
import com.recording.core.model.Order;
import com.recording.core.model.Settings;
import com.recording.core.model.enums.OrderStatus;
import com.recording.core.model.enums.SettingName;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DBServiceSettings;
import com.recording.core.service.DbServiceAvailableSlot;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AvailableSlotUtil {
    private final DBServiceSettings serviceSettings;
    private final DbServiceAvailableSlot serviceAvailableSlot;
    private final DBServiceOrder orderService;

    public AvailableSlotUtil(DBServiceSettings serviceSettings, DbServiceAvailableSlot serviceAvailableSlot, DBServiceOrder orderService) {
        this.serviceSettings = serviceSettings;
        this.serviceAvailableSlot = serviceAvailableSlot;
        this.orderService = orderService;
    }

    public void refreshSlots() {
        Calendar nowDay = Calendar.getInstance();
        Settings step = serviceSettings.getSettingsByName(SettingName.STEP.name());
        Settings start = serviceSettings.getSettingsByName(SettingName.START_WORK.name());
        Settings end = serviceSettings.getSettingsByName(SettingName.END_WORK.name());
        Settings dayForward = serviceSettings.getSettingsByName(SettingName.NEXT_DAY.name());
        if (step != null && start != null && end != null && dayForward != null) {
            int day = Integer.parseInt(dayForward.getValue());
            while (day >= 0) {
                Date targetDay = DateTransform.getAddedDayCalendar(nowDay.getTime(), day);
                targetDay = DateTransform.clearTime(targetDay);

                Date startTime = DateTransform.getAddedHourCalendar(targetDay, Integer.parseInt(start.getValue()));
                Date endTime = DateTransform.getAddedHourCalendar(targetDay, Integer.parseInt(end.getValue()));

                while (endTime.after(startTime)) {
                    AvailableSlot availableSlot = new AvailableSlot();
                    availableSlot.setDay(targetDay);
                    availableSlot.setStartPeriod(startTime);
                    startTime = DateTransform.getAddedMinutesCalendar(startTime, Integer.parseInt(step.getValue()));
                    availableSlot.setEndPeriod(startTime);
                    availableSlot.setUserId(1L);
                    availableSlot.setActive(true);
                    availableSlot.setReserved(false);
                    serviceAvailableSlot.saveSlot(availableSlot);
                }
                day--;
            }
        }
    }

    public void createSlotsForwardDay() {
        Calendar nowDay = Calendar.getInstance();
        nowDay.setTime(new Date());
        Settings step = serviceSettings.getSettingsByName(SettingName.STEP.name());
        Settings start = serviceSettings.getSettingsByName(SettingName.START_WORK.name());
        Settings end = serviceSettings.getSettingsByName(SettingName.END_WORK.name());
        Settings dayForward = serviceSettings.getSettingsByName(SettingName.NEXT_DAY.name());

        if (step != null && start != null && end != null && dayForward != null) {
            int day = Integer.parseInt(dayForward.getValue());
            Date targetDay = DateTransform.getAddedDayCalendar(nowDay.getTime(), day);
            targetDay = DateTransform.clearTime(targetDay);

            Date startTime = DateTransform.getAddedHourCalendar(targetDay, Integer.parseInt(start.getValue()));
            Date endTime = DateTransform.getAddedHourCalendar(targetDay, Integer.parseInt(end.getValue()));

            while (endTime.after(startTime)) {
                AvailableSlot availableSlot = new AvailableSlot();
                availableSlot.setDay(targetDay);
                availableSlot.setStartPeriod(startTime);
                startTime = DateTransform.getAddedMinutesCalendar(startTime, Integer.parseInt(step.getValue()));
                availableSlot.setEndPeriod(startTime);
                availableSlot.setUserId(1L);
                availableSlot.setActive(true);
                availableSlot.setReserved(false);
                serviceAvailableSlot.saveSlot(availableSlot);
            }

        }
    }

    public void setAllSlotNotActive() {
        Optional<List<AvailableSlot>> availableSlotsOp = serviceAvailableSlot.findAllActive();
        List<AvailableSlot> availableSlot = new ArrayList<>();
        availableSlotsOp.ifPresent(availableSlot::addAll);
        for (AvailableSlot slot : availableSlot) {
            slot.setActive(false);
            serviceAvailableSlot.saveSlot(slot);
        }
    }


    public void createOrder(AvailableSlot slot){
        Optional<AvailableSlot> slotOptional = serviceAvailableSlot.findById(slot.getId());
        if(slotOptional.isPresent()){
            AvailableSlot workSlot = slotOptional.get();
            workSlot.setActive(false);
            serviceAvailableSlot.saveSlot(workSlot);
            Order order = new Order(workSlot.getId(),workSlot.getDay(), workSlot.getStartPeriod(), workSlot.getEndPeriod(), OrderStatus.REGISTRATION.getCode());
            orderService.saveOrder(order);
        }
    }
}
