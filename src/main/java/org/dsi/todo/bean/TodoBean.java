package org.dsi.todo.bean;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.dsi.todo.dao.TodoDao;
import org.dsi.todo.entity.Todo;

import java.io.Serializable;

@Named
@ViewScoped
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
}
