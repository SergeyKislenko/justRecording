package com.recording.core.service.impl;

import com.recording.core.model.*;
import com.recording.core.model.enums.OrderStatus;
import com.recording.core.model.enums.SettingName;
import com.recording.core.service.*;
import com.recording.core.utils.DateTransform;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DbInitServiceImpl implements DBInitService {
    private final DBServiceUser userService;
    private final DBServiceOrder orderService;
    private final DBServiceSettings serviceSettings;
    private final DbServiceAvailableSlot serviceAvailableSlot;

    public DbInitServiceImpl(DBServiceUser dbServiceUser, DBServiceOrder orderService, DBServiceSettings serviceSettings, DbServiceAvailableSlot serviceAvailableSlot) {
        this.userService = dbServiceUser;
        this.orderService = orderService;
        this.serviceSettings = serviceSettings;
        this.serviceAvailableSlot = serviceAvailableSlot;
    }

    @Override
    public void initDb() {
        userService.saveUser(createUser("admin", "admin", "ADMIN", "admin@justrecording.ru"));
        userService.saveUser(createUser("user", "user", "USER", "user@justrecording.ru"));
        orderService.saveOrder(createOrder(1L));
        orderService.saveOrder(createOrder(2L));
        serviceSettings.saveSettings(Arrays.asList(
                createDefaultSettings(SettingName.NAME_ORG.name(), "Тестовая организация", "Тестовая настоящая организация"),
                createDefaultSettings(SettingName.STEP.name(), "60", "60"),
                createDefaultSettings(SettingName.NEXT_DAY.name(), "1", "1"),
                createDefaultSettings(SettingName.START_WORK.name(), "8", "8"),
                createDefaultSettings(SettingName.END_WORK.name(), "17", "17")));
        createAvailableSlot();
    }

    private User createUser(String userName, String userPassword, String userRole, String email) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassword);
        user.setEmail(email);
        user.setRoles(Collections.singleton(new Role(userRole)));
        return user;
    }

    private Order createOrder(long slotId) {
        Order order = new Order();
        Date now = new Date();
        order.setSlotId(slotId);
        order.setDay(now);
        order.setStartPeriod(now);
        order.setEndPeriod(DateTransform.getAddedMinutesCalendar(now, 15));
        order.setStatus(OrderStatus.REGISTRATION.getCode());
        return order;
    }

    private Settings createDefaultSettings(String name, String defaultValue, String value) {
        Settings settings = new Settings();
        settings.setName(name);
        settings.setDefaultValue(defaultValue);
        settings.setValue(value);
        return settings;
    }

    private void createAvailableSlot() {
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
}
