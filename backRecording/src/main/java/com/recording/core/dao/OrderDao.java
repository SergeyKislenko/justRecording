package com.recording.core.dao;

import com.recording.core.model.Order;
import com.recording.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<List<Order>> findAll();

    void insertOrUpdate(Order user);

    SessionManager getSessionManager();
}
