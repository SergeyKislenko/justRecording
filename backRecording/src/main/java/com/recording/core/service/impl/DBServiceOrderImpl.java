package com.recording.core.service.impl;

import com.recording.core.dao.OrderDao;
import com.recording.core.exception.DbServiceException;
import com.recording.core.model.Order;
import com.recording.core.service.DBServiceOrder;
import com.recording.hibernate.sessionmanager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceOrderImpl implements DBServiceOrder {

    private static Logger logger = LoggerFactory.getLogger(DBServiceOrderImpl.class);

    private final OrderDao orderDao;

    public DBServiceOrderImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Optional<List<Order>> findAll() {
        try (SessionManager sessionManager = orderDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<Order>> orderOptional = orderDao.findAll();
                return orderOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public long saveOrder(Order order) {
        try (SessionManager sessionManager = orderDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                orderDao.insertOrUpdate(order);
                long orderId = order.getId();
                sessionManager.commitSession();
                logger.info("created order: {}", orderId);
                return orderId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void changeOrderStatus(long orderId, String status) {
        try (SessionManager sessionManager = orderDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                orderDao.changeOrderStatus(orderId, status);
                sessionManager.commitSession();
                logger.info("status chanced: {}", orderId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}
