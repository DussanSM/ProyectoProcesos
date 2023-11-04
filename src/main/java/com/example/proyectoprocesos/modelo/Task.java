package com.example.proyectoprocesos.modelo;

public class Task {
    private boolean mandatory;
    private String description;
    private double time;

    public Task(String description, boolean mandatory, double time) {
        this.description = description;
        this.mandatory = mandatory;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
