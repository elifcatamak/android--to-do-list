package com.example.todolistpractice.model;

public class ToDoItem {
    private int id;
    private String name;
    private boolean isDone;
    private String dateAdded;
    private int listId;

    public ToDoItem() {
    }

    public ToDoItem(String name, boolean isDone, int listId) {
        this.name = name;
        this.isDone = isDone;
        this.listId = listId;
    }

    public ToDoItem(String name, boolean isDone, String dateAdded, int listId) {
        this.name = name;
        this.isDone = isDone;
        this.dateAdded = dateAdded;
        this.listId = listId;
    }

    public ToDoItem(int id, String name, boolean isDone, String dateAdded, int listId) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
        this.dateAdded = dateAdded;
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
