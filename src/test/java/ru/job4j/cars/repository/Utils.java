package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import ru.job4j.cars.configuration.HibernateConfiguration;

public class Utils {
    public static SessionFactory createSessionFactory() {
        return new HibernateConfiguration().sf();
    }

    public static void closeSessionFactory(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
