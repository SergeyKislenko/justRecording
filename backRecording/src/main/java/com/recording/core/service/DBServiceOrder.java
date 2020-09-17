package com.recording.core.service;

import com.recording.core.model.Order;

import java.util.List;
import java.util.Optional;

public interface DBServiceOrder {
    Optional<List<Order>> findAll();
    long saveOrder(Order user);
    void changeOrderStatus(long id, String status);
}
