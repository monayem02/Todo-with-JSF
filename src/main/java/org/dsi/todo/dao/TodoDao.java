package org.dsi.todo.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.dsi.todo.entity.Todo;

import java.sql.SQLException;
import java.util.List;

@Stateless
public class TodoDao {
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("postgres").createEntityManager();
        }
        return entityManager;
    }

    private EntityTransaction getEntityTransaction() {
        return getEntityManager().getTransaction();
    }

    public Todo createTodo(Todo todo) throws SQLException {
        EntityTransaction transaction = getEntityTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            getEntityManager().persist(todo);
            transaction.commit();
            return todo;
        } catch (Exception e) {
            System.out.println("Error while creating todo: " + e.getMessage());
            transaction.rollback();
            throw new SQLException("Error in creating todo: " + e.getMessage());
        }
    }

    public List<Todo> getTodos() throws SQLException {
        try {
            return getEntityManager()
                    .createQuery("SELECT t FROM Todo t", Todo.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error while fetching todos: " + e.getMessage());
            throw new SQLException("Error in fetching todos: " + e.getMessage());
        }
    }

//    private void search(String title,String status) throws SQLException {
//        StringBuilder query = new StringBuilder("SELECT t FROM Todo t");
//        StringBuilder whereClause = new StringBuilder("where");
//        if (title != null) {
//            query.append("t.title = :title");
//            query.append(" AND ");
//        }
//        if (status != null) {
//            query.append("  t.status = :status");
//        }
//    }

    public void deleteTodo(Long id) throws SQLException {
        EntityTransaction transaction = getEntityTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            Todo todo = getEntityManager().find(Todo.class, id);
            if (todo != null) {
                getEntityManager().remove(todo);
                getEntityManager().flush();
                transaction.commit();
            }
        } catch (Exception e) {
            System.out.println("Error while deleting todo: " + e.getMessage());
            transaction.rollback();
            throw new SQLException("Error in deleting todo: " + e.getMessage());
        }
    }

    public Todo updateTodo(Long id, Todo updatedTodo) throws SQLException {
        EntityTransaction transaction = getEntityTransaction();

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            Todo existingTodo = getEntityManager().find(Todo.class, id);
            if (existingTodo != null) {
                existingTodo.setTitle(updatedTodo.getTitle());
                existingTodo.setDescription(updatedTodo.getDescription());
                existingTodo.setStatus(updatedTodo.getStatus());

                getEntityManager().merge(existingTodo);
                getEntityManager().flush();
                transaction.commit();

                return existingTodo;
            } else {
                throw new SQLException("Todo with id " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error while updating todo: " + e.getMessage());
            transaction.rollback();
            throw new SQLException("Error in updating todo: " + e.getMessage());
        }
    }

    public Todo getTodo(Long id) {
        return getEntityManager().find(Todo.class, id);
    }

}
