package com.example.todolistpractice.model;

public class ToDoItem {
    private long id;
    private String name;
    private boolean isDone;
    private String dateAdded;
    private long listId;

    public ToDoItem() {
    }

    public ToDoItem(String name, boolean isDone, long listId) {
        this.name = name;
        this.isDone = isDone;
        this.listId = listId;
    }

    public ToDoItem(String name, boolean isDone, String dateAdded, long listId) {
        this.name = name;
        this.isDone = isDone;
        this.dateAdded = dateAdded;
        this.listId = listId;
    }

    public ToDoItem(long id, String name, boolean isDone, String dateAdded, long listId) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
        this.dateAdded = dateAdded;
        this.listId = listId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }
}
