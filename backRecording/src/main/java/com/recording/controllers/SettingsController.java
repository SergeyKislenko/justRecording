package com.recording.controllers;

import com.recording.core.model.Settings;
import com.recording.core.service.DBServiceSettings;
import com.recording.core.utils.AvailableSlotUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class SettingsController {
    private final DBServiceSettings serviceSettings;
    private final AvailableSlotUtil availableSlotUtil;

    public SettingsController(DBServiceSettings serviceSettings, AvailableSlotUtil availableSlotUtil) {
        this.serviceSettings = serviceSettings;
        this.availableSlotUtil = availableSlotUtil;
    }

    @PostMapping(path = "/settings/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(HttpServletResponse response, @RequestBody List<Settings> settings) throws IOException {
        serviceSettings.saveSettings(settings);
        availableSlotUtil.setAllSlotNotActive();
        availableSlotUtil.refreshSlots();
        response.sendRedirect("/orders");
    }
}
