package org.dsi.todowithjsf.bean;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.dsi.todowithjsf.dao.TodoDao;
import org.dsi.todowithjsf.entity.Todo;

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
