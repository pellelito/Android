package com.example.todolist;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> list = new ArrayList<Task>();
    static private final String insideTaskDelimiter = "qwxzwezcx";
    static private final String outsideTaskDelimiter = "posdqwexqw";

    public void addTask(Task task){
        list.add(task);
    }

    public Task getTask(int index){
        return list.get(index);
    }

    public String getItemName(int index){
        return list.get(index).getName();
    }

    public String getItemDescription(int index){
        return list.get(index).getDescription();
    }

    public boolean toggleChecked(int index){
        return list.get(index).toggleChecked();
    }

    public boolean isChecked(int index){
        return list.get(index).isChecked();
    }

    public int size(){
        return list.size();
    }

    public void deleteChecked() {
        for (int i = 0; i < list.size(); ++i){
            if (list.get(i).isChecked()) {
                list.remove(i);
                --i;
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (Task t : list)
            sb.append(t.getName() + insideTaskDelimiter + t.getDescription() + outsideTaskDelimiter);


        return sb.toString();
    }

    static public TaskList fromString(String str) {
        TaskList taskList = new TaskList();
        String[] tasksString = str.split(outsideTaskDelimiter);

        for (String s : tasksString){
            if (!s.equals("")){
                String[] taskString = s.split(insideTaskDelimiter);

                if (taskString.length >= 2)
                    taskList.addTask(new Task(taskString[0], taskString[1]));
            }
        }
        return taskList;
    }
}
