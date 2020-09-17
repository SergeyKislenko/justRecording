package com.recording.hibernate.dao;

import com.recording.core.dao.SettingsDao;
import com.recording.core.model.Settings;
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
public class SettingsDaoHibernate implements SettingsDao {

    private static Logger logger = LoggerFactory.getLogger(SettingsDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public SettingsDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<List<Settings>> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder cb = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<Settings> cq = cb.createQuery(Settings.class);
            Root<Settings> rootEntry = cq.from(Settings.class);
            CriteriaQuery<Settings> all = cq.select(rootEntry);
            TypedQuery<Settings> allQuery = currentSession.getHibernateSession().createQuery(all);
            return Optional.of(allQuery.getResultList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Settings findByName(String name) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            CriteriaBuilder builder = currentSession.getHibernateSession().getCriteriaBuilder();
            CriteriaQuery<Settings> criteria = builder.createQuery(Settings.class);
            Root<Settings> root = criteria.from(Settings.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("name"), name));
            TypedQuery<Settings> typed = currentSession.getHibernateSession().createQuery(criteria);
            Settings settings =  typed.getSingleResult();
            return settings;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void insertOrUpdate(List<Settings> settings) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            for (Settings setting : settings) {
                if (findByName(setting.getName()) != null) {
                    Settings s = findByName(setting.getName());
                    s.setValue(setting.getValue());
                    hibernateSession.merge(s);
                } else {
                    hibernateSession.persist(setting);
                    hibernateSession.flush();
                }
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
