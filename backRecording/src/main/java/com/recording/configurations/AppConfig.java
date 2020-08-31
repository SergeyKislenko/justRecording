package com.recording.configurations;

import com.recording.core.model.Role;
import com.recording.core.model.User;
import com.recording.core.service.DBInitServise;
import com.recording.core.service.DBInitServiseImpl;
import com.recording.core.service.DBServiceUser;
import com.recording.hibernate.HibernateUtils;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "initUserDb")
    public DBInitServise dbInitServise(DBServiceUser dbServiceUser) {
        return new DBInitServiseImpl(dbServiceUser);
    }

    @Bean
    public SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = HibernateUtils
                .buildSessionFactory("hibernate.cfg.xml", User.class, Role.class);
        return sessionFactory;
    }
}
