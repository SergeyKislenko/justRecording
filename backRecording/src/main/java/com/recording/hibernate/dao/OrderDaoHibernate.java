package com.recording.hibernate.dao;


import com.recording.core.dao.OrderDao;
import com.recording.core.exception.UserDaoException;
import com.recording.core.model.Order;
import com.recording.hibernate.sessionmanager.DatabaseSessionHibernate;
import com.recording.hibernate.sessionmanager.SessionManager;
import com.recording.hibernate.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoHibernate implements OrderDao {

    private static Logger logger = LoggerFactory.getLogger(OrderDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public OrderDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<List<Order>> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> rootEntry = cq.from(Order.class);
            CriteriaQuery<Order> all = cq.select(rootEntry);
            TypedQuery<Order> allQuery = currentSession.getHibernateSession().createQuery(all);
            return Optional.of(allQuery.getResultList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void insertOrUpdate(Order order) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (order.getId() > 0) {
                hibernateSession.merge(order);
            } else {
                hibernateSession.persist(order);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> model = cq.from(Order.class);
            cq.where(cb.equal(model.get("id"), id));
            TypedQuery<Order> q = currentSession.getHibernateSession().createQuery(cq);
            return Optional.of(q.getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void changeOrderStatus(long id, String status) {
        Optional<Order> optOrder = findById(id);
        if (optOrder.isPresent()){
            Order order = optOrder.get();
            order.setStatus(status);
            insertOrUpdate(order);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
