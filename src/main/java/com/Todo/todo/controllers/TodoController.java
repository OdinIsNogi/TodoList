package com.Todo.todo.controllers;

import com.Todo.todo.domain.Todo;
import com.Todo.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping({"/", "/list"})
    public ModelAndView getAll() {
        ModelAndView mav = new ModelAndView("first-view");
        List<Todo> list = todoService.getAll();
        mav.addObject("todos", list);
        return mav;
    }

    @GetMapping("/addTodoForm")
    public ModelAndView addTodoForm() {
        ModelAndView mav = new ModelAndView("add-todo-form");
        Todo newTodo = new Todo();
        mav.addObject("todo", newTodo);
        return mav;
    }

    @PostMapping("/saveTodo")
    public String saveTodo(@ModelAttribute Todo todo) {
        todoService.create(todo);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long todoId) {
        ModelAndView mav = new ModelAndView("update-todo-form");
        Todo todo = todoService.get(todoId).get();
        mav.addObject("todo", todo);
        return mav;
    }

    @PostMapping("/updateTodo")
    public String updateTodo(@ModelAttribute Todo todo) {
        todoService.update(todo);
        return "redirect:/list";
    }

    @GetMapping("/deleteTodo")
    public String deleteTodo(@RequestParam Long todoId) {
        todoService.delete(todoId);
        return "redirect:/list";
    }

}
