package com.example.todolistpractice.model;

public class ToDoList {
    int id;
    String listName;
    String dateAdded;

    public ToDoList() {
    }

    public ToDoList(String listName, String dateAdded) {
        this.listName = listName;
        this.dateAdded = dateAdded;
    }

    public ToDoList(int id, String listName, String dateAdded) {
        this.id = id;
        this.listName = listName;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
