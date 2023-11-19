package com.example.proyectoprocesos.modelo;

import com.example.proyectoprocesos.modelo.estructuraDatos.DoubleList;

import java.util.Iterator;

public class Process {
    private DoubleList<Activity> activities;
    private String id;
    private String name;

    public Process(String id, String name) {
        this.id = id;
        this.name = name;
        activities = null;
    }

    public void addActivityEnd(Activity activity){
        if (!onlyName(activity.getName())){
            return;
        }

        activities.addEnd(activity);
    }

    public void addActivityRecent(Activity activity){
        if (!onlyName(activity.getName())){
            return;
        }

        activities.addNode(activity, activities.getLastNodeRegister());
    }

    public void addActivity(Activity activity, String activityName){
        if (!onlyName(activity.getName())){
            return;
        }

        DoubleList<Activity>.IteratorDoubleList it = (DoubleList<Activity>.IteratorDoubleList) activities.iterator();
        int index = -1;
        while(it.hasNext()){
            int node = it.getPosition();
            Activity ac = (Activity) it.next();
            if(ac.getName().equalsIgnoreCase(activityName)){
                index = node;
            }
        }

        if(index != -1){
            activities.add(activity, index);
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
