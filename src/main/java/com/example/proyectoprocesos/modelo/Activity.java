package com.example.proyectoprocesos.modelo;

import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;

import java.util.Iterator;

public class Activity {
    private String name;
    private String description;
    private boolean mandatory;

    private Queue<Task> tasks;

    public Activity(String name, String description, boolean mandatory) {
        this.name = name;
        this.description = description;
        this.mandatory = mandatory;
        tasks = new Queue<>();
    }

    public void pushTask(Task task){
        if (tasks.isEmpty()){
            tasks.push(task);
            return;
        }

        if(checkOptional(task, tasks.end())) {
            tasks.push(task);
        }
    }

    public void pushTask(Task task, int index){
        if(tasks.getSize() < index){
            return;
        }

        Queue<Task> newQueue = new Queue<>();
        Queue<Task>.IteratorQueue it = (Queue<Task>.IteratorQueue) tasks.iterator();
        while(it.hasNext()){
            int position = it.getPosition();
            Task tk = it.next();
            if(index == position && checkOptional(task, tk)){
                newQueue.push(task);
            }
            newQueue.push(tk);
        }

        tasks = newQueue;
    }

    public boolean checkOptional(Task taskNext, Task task){
        if(!taskNext.isMandatory() && !task.isMandatory()){
            System.out.println("No pueden existir dos tareas opcionales seguidas");
            //throw new RuntimeException("No pueden existir dos tareas opcionales seguidas");
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Queue<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString(){
        return name;
    }
}
