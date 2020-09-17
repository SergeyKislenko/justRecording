package com.recording.core.service.impl;

import com.recording.core.dao.SettingsDao;
import com.recording.core.exception.DbServiceException;
import com.recording.core.model.Settings;
import com.recording.core.service.DBServiceSettings;
import com.recording.hibernate.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbServiceSettingsImpl implements DBServiceSettings {
    private static Logger logger = LoggerFactory.getLogger(DbServiceSettingsImpl.class);

    private final SettingsDao settingsDao;

    public DbServiceSettingsImpl(SettingsDao settingsDao) {
        this.settingsDao = settingsDao;
    }


    @Override
    public boolean saveSettings(List<Settings> settings) {
        try (SessionManager sessionManager = settingsDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                settingsDao.insertOrUpdate(settings);
                sessionManager.commitSession();
                logger.info("created settings");
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Settings getSettingsByName(String name) {
        try (SessionManager sessionManager = settingsDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Settings settings = settingsDao.findByName(name);
                logger.info("settings: {}", settings);
                return settings;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    @Override
    public Optional<List<Settings>> findAll() {
        try (SessionManager sessionManager = settingsDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<Settings>> settingOptional = settingsDao.findAll();
                return settingOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
