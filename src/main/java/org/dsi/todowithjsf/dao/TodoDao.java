package org.dsi.todowithjsf.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.dsi.todowithjsf.entity.Todo;

import java.sql.SQLException;

@Stateless
public class TodoDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();
        }
        return entityManager;
    }

    private EntityTransaction getEntityTransaction() {
        return entityTransaction =  getEntityManager().getTransaction();
    }

    public Todo createTodo(Todo todo) throws SQLException {
        if (!getEntityTransaction().isActive()) {
            getEntityTransaction().begin();
        }
        try {
            getEntityManager().persist(todo);
            getEntityManager().flush();
            entityTransaction.commit();
            return todo;
        } catch (Exception e) {
            System.out.println("Error while creating todo: " + e.getMessage());
            if (!getEntityTransaction().isActive()) {
                getEntityTransaction().begin();
            }
            getEntityTransaction().rollback();
            throw new SQLException("Error in creating todo: " + e.getMessage());
        }
    }
}
