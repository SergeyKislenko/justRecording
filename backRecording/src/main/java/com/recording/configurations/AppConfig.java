package com.recording.configurations;

import com.recording.core.model.*;
import com.recording.core.model.enums.OrderStatus;
import com.recording.core.service.*;
import com.recording.core.service.impl.DbInitServiceImpl;
import com.recording.hibernate.HibernateUtils;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "initDb")
    public DBInitService dbInitServise(DBServiceUser dbServiceUser, DBServiceOrder dbServiceOrder, DBServiceSettings dbServiceSettings, DbServiceAvailableSlot serviceAvailableSlot) {
        return new DbInitServiceImpl(dbServiceUser, dbServiceOrder, dbServiceSettings, serviceAvailableSlot);
    }

    @Bean
    public SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = HibernateUtils
                .buildSessionFactory("hibernate.cfg.xml", User.class, Role.class, Order.class, OrderStatus.class, Settings.class, AvailableSlot.class);
        return sessionFactory;
    }
}
