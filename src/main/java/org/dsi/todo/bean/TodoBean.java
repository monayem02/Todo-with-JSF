package org.dsi.todo.bean;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import org.dsi.todo.dao.TodoDao;
import org.dsi.todo.entity.Todo;
import org.dsi.todo.helper.Status;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
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

    public String prepareCreateTodo() {
        todo = new Todo();
        return "create-todo.xhtml?faces-redirect=true";
    }

    public String createTodo() throws Exception {
        todoDao.createTodo(todo);
        todo = new Todo();
        return "index.xhtml?faces-redirect=true";
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
            todo = new Todo();
            return "index.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SelectItem> getStatuses() {
        return Arrays.stream(Status.values())
                .map(status -> new SelectItem(status, status.getLabel()))
                .toList();
    }
}
