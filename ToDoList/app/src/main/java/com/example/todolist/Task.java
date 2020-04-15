package com.example.todolist;

public class Task {

    private String name;
    private String desc;
    private boolean checked;

    public Task(String name, String desc){
        this.name = name;
        this.desc = desc;
        checked = false;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return desc;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean toggleChecked(){
        return checked = !checked;
    }
}
