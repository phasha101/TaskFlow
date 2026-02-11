package com.taskflow.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().addAnnotatedClass(com.taskflow.model.Task.class).
                    addAnnotatedClass(com.taskflow.model.Priority.class).
                    addAnnotatedClass(com.taskflow.model.Status.class).
                    addAnnotatedClass(com.taskflow.model.Category.class)
                    .configure().buildSessionFactory();
        } catch (HibernateException ex) {
            // Log the exception (use a logger in real projects)
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
