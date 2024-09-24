package org.dsi.todo.bean;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.dsi.todo.dao.TodoDao;
import org.dsi.todo.entity.Todo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Named
@ApplicationScoped
public class TodoBean implements Serializable {
    private Todo todo;
    @EJB
    private TodoDao todoDao;

    public Todo getTodo() {
        if (todo == null) {
            todo = new Todo();
        }
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public void createTodo() throws Exception {
        todoDao.createTodo(todo);
        todo = new Todo();
    }

    public List<Todo> getTodoList() throws SQLException {
        return todoDao.getTodos();
    }

    public void deleteTodo(Todo todo) throws SQLException {
        todoDao.deleteTodo(todo.getId());
    }

    public String editTodo(long id) throws SQLException {
        this.todo = todoDao.getTodo(id);
        return "update-todo.xhtml?faces-redirect=true";
    }

    public String updateTodo(Long id, Todo updatedTodo){
        try {
            todoDao.updateTodo(id, updatedTodo);
            return "index.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
