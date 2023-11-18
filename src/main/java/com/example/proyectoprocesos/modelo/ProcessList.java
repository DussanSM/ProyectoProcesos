package com.example.proyectoprocesos.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProcessList implements Iterable<Process>{
    private static ProcessList instance;
    private List<Process> processList;

    private ProcessList() {
        processList = new ArrayList<>();
    }

    public static ProcessList getInstance() {
        if (instance == null) {
            instance = new ProcessList();
        }
        return instance;
    }

    public void addProcess(Process process) {
        processList.add(process);
    }

    public void removeProcess(Process process) {
        processList.remove(process);
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void update(Process process, Process newProcess){
        int index = processList.indexOf(process);
        processList.remove(process);
        processList.add(index, newProcess);
    }

    @Override
    public Iterator<Process> iterator() {
        return processList.iterator();
    }
}
