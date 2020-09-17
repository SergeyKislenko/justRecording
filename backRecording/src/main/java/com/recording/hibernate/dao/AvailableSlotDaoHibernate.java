package com.recording.hibernate.dao;

import com.recording.core.dao.AvailableSlotDao;
import com.recording.core.model.AvailableSlot;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AvailableSlotDaoHibernate implements AvailableSlotDao {
    private static Logger logger = LoggerFactory.getLogger(AvailableSlotDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public AvailableSlotDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<List<AvailableSlot>> findAllByDay(Date day) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<AvailableSlot> cq = cb.createQuery(AvailableSlot.class);
            Root<AvailableSlot> rootEntry = cq.from(AvailableSlot.class);
            cq.select(rootEntry);
            cq.where(cb.equal(rootEntry.get("day"), day), cb.equal(rootEntry.get("active"), true));
            TypedQuery<AvailableSlot> allQuery = currentSession.getHibernateSession().createQuery(cq);
            return Optional.of(allQuery.getResultList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<AvailableSlot>> findAllDay() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<AvailableSlot> cq = cb.createQuery(AvailableSlot.class);
            Root<AvailableSlot> rootEntry = cq.from(AvailableSlot.class);
            cq.select(rootEntry.get("day")).distinct(true);
            cq.where(cb.equal(rootEntry.get("active"), true));
            TypedQuery<AvailableSlot> allQuery = currentSession.getHibernateSession().createQuery(cq);
            return Optional.of(allQuery.getResultList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<AvailableSlot> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<AvailableSlot> cq = cb.createQuery(AvailableSlot.class);
            Root<AvailableSlot> rootEntry = cq.from(AvailableSlot.class);
            cq.select(rootEntry);
            cq.where(cb.equal(rootEntry.get("id"), id));
            TypedQuery<AvailableSlot> allQuery = currentSession.getHibernateSession().createQuery(cq);
            return Optional.of(allQuery.getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void insertOrUpdate(AvailableSlot slot) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (slot.getId() > 0) {
                hibernateSession.merge(slot);
            } else {
                hibernateSession.persist(slot);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
