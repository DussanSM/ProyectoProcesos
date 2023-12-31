package com.example.proyectoprocesos.modelo;

import com.example.proyectoprocesos.modelo.estructuraDatos.DoubleNode;
import com.example.proyectoprocesos.modelo.estructuraDatos.Node;
import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;

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

    public void importFromCsv(String filePath) {
        CsvService<Activity> csvService = new CsvService<>();

        this.processList.removeAll(processList);
        List<String[]> importedData = csvService.importFromCsv(filePath);

        if (importedData.size() >= 2) {
            try {
                int i = 0;
                boolean passTask = false;
                do {
                    ++i;
                    String[] processData = importedData.get(i);
                    if (processData.length == 2) {
                        if (!processData[0].equals("Name")) {
                            addProcess(new Process(processData[1], processData[0]));
                            passTask = false;
                        }
                    }

                    if(processData[0].equals("Activity")){
                        passTask = false;
                    }

                    if(processData[0].equals("Task")){
                        passTask = true;
                    }

                    if(processData.length == 3 && !passTask){
                        boolean b = processData[2].equals("true") ? true : false;
                        if (!processData[0].equals("Activity")){
                            Process p = processList.get(processList.size()-1);
                            p.addActivityRecent(new Activity(processData[0], processData[1], b));
                        }
                    }

                    if(processData.length == 3 && passTask){
                        if (!processData[0].equals("Task")) {
                            DoubleNode<Activity> node = processList.get(processList.size() - 1).getActivities().getLastNode();
                            boolean b = processData[1].equals("true") ? true : false;
                            Activity a = node.getValue();
                            a.pushTask(new Task(processData[0], b, Double.parseDouble(processData[2])));
                        }
                    }
                }while (importedData.size() > i);
            }catch (Exception e){

            }
        }

        this.refreshTime();
    }

    public void exportToCsv(String filePath) {
        List<String[]> data = new ArrayList<>();

        for(Process p : this.processList) {
            data.add(new String[]{"Name", "ID"});
            data.add(new String[]{p.getName(), p.getId()});
            for (Activity a: p.getActivities()) {
                data.add(new String[]{"Activity", "Description", "Mandatory"});
                data.add(new String[]{a.getName(), a.getDescription(), a.isMandatory()+""});
                Queue<Task> tasks = a.getTasks().clone();
                for (Task t: tasks){
                    data.add(new String[]{"Task", "Mandatory", "Time"});
                    data.add(new String[]{t.getDescription(), t.isMandatory()+"", t.getTime()+""});
                }
            }
        }

        CsvService<Activity> csvService = new CsvService<>();
        csvService.exportToCsv(filePath, data);
    }

    public void refreshTime(){
        for (Process p: this.processList) {
            p.calculateMinTime();
        }
    }
    @Override
    public Iterator<Process> iterator() {
        return processList.iterator();
    }
}
