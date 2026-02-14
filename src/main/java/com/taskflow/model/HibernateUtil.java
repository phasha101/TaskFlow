package com.taskflow.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Add annotated classes
            configuration.addAnnotatedClass(com.taskflow.model.Task.class);
            configuration.addAnnotatedClass(com.taskflow.model.Priority.class);
            configuration.addAnnotatedClass(com.taskflow.model.Status.class);
            configuration.addAnnotatedClass(com.taskflow.model.Category.class);

            // Load defaults from hibernate.cfg.xml
            configuration.configure();

            // Override with environment variables if present
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbUrl != null) {
                configuration.setProperty("hibernate.connection.url", dbUrl);
            }
            if (dbUser != null) {
                configuration.setProperty("hibernate.connection.username", dbUser);
            }
            if (dbPassword != null) {
                configuration.setProperty("hibernate.connection.password", dbPassword);
            }

            return configuration.buildSessionFactory();
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
