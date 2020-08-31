package com.recording.core.dao;

import com.recording.core.model.User;
import com.recording.hibernate.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<List<User>> findAll();

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
