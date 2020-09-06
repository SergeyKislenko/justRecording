package com.recording.configurations;

import com.recording.core.model.Order;
import com.recording.core.model.OrderStatus;
import com.recording.core.model.Role;
import com.recording.core.model.User;
import com.recording.core.service.DBInitServise;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DBServiceUser;
import com.recording.core.service.impl.DbInitServiseImpl;
import com.recording.hibernate.HibernateUtils;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean(initMethod = "initDb")
    public DBInitServise dbInitServise(DBServiceUser dbServiceUser, DBServiceOrder dbServiceOrder) {
        return new DbInitServiseImpl(dbServiceUser, dbServiceOrder);
    }

    @Bean
    public SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = HibernateUtils
                .buildSessionFactory("hibernate.cfg.xml", User.class, Role.class, Order.class, OrderStatus.class);
        return sessionFactory;
    }
}
