package com.taskflow.repository;

import com.taskflow.model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

import static com.taskflow.model.HibernateUtil.getSessionFactory;

public class TaskRepository {


    public void save(Task task){
        try(Session session = getSessionFactory().openSession()){
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();}
    }

    public void update(Task task){
        try(Session session = getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();}
    }

    public List<Task> findAll(){
       try(Session session = getSessionFactory().openSession()) {
           return session.createQuery("from Task", Task.class).list();
       }
    }

    public Task findById(UUID id){
        try (Session session = getSessionFactory().openSession()){
            return session.find(Task.class, id);
        }
    }

    public void delete(UUID id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Task task = session.find(Task.class, id);
            if (task != null) {
                session.remove(task);
                System.out.println("Task of id: " + id + " removed");
            } else {
                System.out.println("Task of id: " + id + " not found");
            }
            transaction.commit();
        }
    }

}