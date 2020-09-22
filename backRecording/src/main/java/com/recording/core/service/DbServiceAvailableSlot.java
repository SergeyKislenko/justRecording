package com.recording.core.service;

import com.recording.core.model.AvailableSlot;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface
DbServiceAvailableSlot {
    Optional<List<AvailableSlot>> findAllByDay(Date day);
    Optional<List<AvailableSlot>> findAllActive();
    Optional<AvailableSlot> findById(long id);
    Optional<List<AvailableSlot>> findAllDay();
    long saveSlot(AvailableSlot user);
}
