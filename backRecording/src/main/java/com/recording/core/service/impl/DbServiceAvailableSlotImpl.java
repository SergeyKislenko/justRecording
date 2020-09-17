package com.recording.core.service.impl;

import com.recording.core.dao.AvailableSlotDao;
import com.recording.core.exception.DbServiceException;
import com.recording.core.model.AvailableSlot;
import com.recording.core.service.DbServiceAvailableSlot;
import com.recording.hibernate.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceAvailableSlotImpl implements DbServiceAvailableSlot {

    private static Logger logger = LoggerFactory.getLogger(DbServiceAvailableSlotImpl.class);
    private final AvailableSlotDao availableSlotDao;

    public DbServiceAvailableSlotImpl(AvailableSlotDao availableSlotDao) {
        this.availableSlotDao = availableSlotDao;
    }

    @Override
    public Optional<List<AvailableSlot>> findAllByDay(Date day) {
        try (SessionManager sessionManager = availableSlotDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<AvailableSlot>> availableSlotOptional = availableSlotDao.findAllByDay(day);
                logger.info("availableSlot: {}", availableSlotOptional.orElse(null));
                return availableSlotOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<AvailableSlot> findById(long id) {
        try (SessionManager sessionManager = availableSlotDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<AvailableSlot> availableSlotOptional = availableSlotDao.findById(id);
                logger.info("availableSlot: {}", availableSlotOptional.orElse(null));
                return availableSlotOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<AvailableSlot>> findAllDay() {
        try (SessionManager sessionManager = availableSlotDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<AvailableSlot>> availableSlotOptional = availableSlotDao.findAllDay();
                logger.info("availableDayForSlot: {}", availableSlotOptional.orElse(null));
                return availableSlotOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public long saveSlot(AvailableSlot slot) {
        try (SessionManager sessionManager = availableSlotDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                availableSlotDao.insertOrUpdate(slot);
                long slotId = slot.getId();
                sessionManager.commitSession();
                logger.info("created slot: {}", slotId);
                return slotId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}
