package com.recording.core.dao;

import com.recording.core.model.Settings;
import com.recording.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface SettingsDao {
    Optional<List<Settings>> findAll();

    Settings findByName(String name);

    void insertOrUpdate(List<Settings> settings);

    SessionManager getSessionManager();
}
