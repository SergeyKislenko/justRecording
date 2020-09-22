package com.recording.core.dao;

import com.recording.core.model.AvailableSlot;
import com.recording.hibernate.sessionmanager.SessionManager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AvailableSlotDao {
    Optional<List<AvailableSlot>> findAllByDay(Date day);

    Optional<List<AvailableSlot>> findAllDay();

    Optional<List<AvailableSlot>> findAllActive();

    Optional<AvailableSlot> findById(long id);

    void insertOrUpdate(AvailableSlot user);

    SessionManager getSessionManager();
}
