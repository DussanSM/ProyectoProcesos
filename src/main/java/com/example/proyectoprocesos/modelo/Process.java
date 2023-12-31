package com.example.proyectoprocesos.modelo;

import com.example.proyectoprocesos.modelo.estructuraDatos.DoubleList;
import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;

import java.util.Iterator;
import java.util.List;

public class Process {
    private DoubleList<Activity> activities;
    private String id;
    private String name;
    private ProcessList processList;
    private double minTime;
    private boolean manualTime;

    public Process(String id, String name) {
        this.processList = ProcessList.getInstance();
        this.id = id;
        this.name = name;
        this.minTime = 1;
        this.manualTime = false;
        activities = new DoubleList<>();
    }

    public void addActivityEnd(Activity activity){
        if (!onlyName(activity.getName())){
            return;
        }

        activity = referenceActivity(activity);
        activities.addEnd(activity);
        this.calculateMinTime();
    }

    public void addActivityRecent(Activity activity){
        if (!onlyName(activity.getName())){
            return;
        }

        activity = referenceActivity(activity);

        if (activities.getLastNodeRegister() == activities.getLastNode()){
            activities.addEnd(activity);
            return;
        }

        activities.addNode(activity, activities.getLastNodeRegister());
        this.calculateMinTime();
    }

    public void addActivity(Activity activity, String activityName){
        if (!onlyName(activity.getName())){
            return;
        }

        activity = referenceActivity(activity);

        if (activities.getSize() == 1){
            activities.addEnd(activity);
            return;
        }

        DoubleList<Activity>.IteratorDoubleList it = (DoubleList<Activity>.IteratorDoubleList) activities.iterator();
        int index = -1;
        while(it.hasNext()){
            int node = it.getPosition();
            Activity ac = (Activity) it.next();
            if(ac.getName().equalsIgnoreCase(activityName)){
                index = node+1;
                break;
            }
        }

        if(index != -1){
            activities.add(activity, index);
        }

        this.calculateMinTime();
    }

    public boolean onlyName(String name){
        Iterator<Activity> it = activities.iterator();
        while(it.hasNext()){
            Activity ac = (Activity) it.next();
            if(ac.getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }

    public boolean onlyNameEdit(String name){
        for (Process process: this.processList.getProcessList()) {
            Iterator<Activity> it = process.getActivities().iterator();
            while(it.hasNext()){
                Activity ac = (Activity) it.next();
                if(ac.getName().equalsIgnoreCase(name)){
                    return false;
                }
            }
        }
        return true;
    }

    public void removeActivity(Activity activity){
        this.activities.removeNode(activity);
        this.calculateMinTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setManualTime(boolean manualTime) {
        this.manualTime = manualTime;
    }

    public double getMinTime() {
        return minTime;
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoubleList<Activity> getActivities(){
        return this.activities;
    }

    public Activity referenceActivity(Activity activity){
        List<Process> processes = this.processList.getProcessList();
        for (Process process: processes) {
            for (Activity a: process.getActivities()) {
                if(a.getName().equals(activity.getName())){
                    a.setMandatory(activity.isMandatory());
                    a.setDescription(activity.getDescription());
                    return a;
                }
            }
        }
        return activity;
    }
    public double calculateMinTime(){
        double sumTime = 0;
        for (Activity a: activities) {
            Queue<Task> tasks = a.getTasks().clone();
            for (Task t: tasks){
                sumTime+=t.getTime();
            }
        }

        if (manualTime) {
            if(this.minTime < sumTime){
                this.minTime = sumTime;
                this.manualTime = false;
            }
        }else {
            this.minTime = sumTime;
        }
        return sumTime;
    }


    @Override
    public String toString(){
        return this.name + " - " + this.id;
    }
}
