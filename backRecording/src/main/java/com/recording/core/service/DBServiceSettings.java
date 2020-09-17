package com.recording.core.service;

import com.recording.core.model.Settings;

import java.util.List;
import java.util.Optional;

public interface DBServiceSettings {

    boolean saveSettings(List<Settings> settings);

    Settings getSettingsByName(String name);

    Optional<List<Settings>> findAll();

}
