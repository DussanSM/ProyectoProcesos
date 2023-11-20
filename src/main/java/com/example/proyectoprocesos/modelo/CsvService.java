package com.example.proyectoprocesos.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvService<T> {

    public void exportToCsv(String filePath, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> importFromCsv(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<T> convertToEntities(List<String[]> rawData, EntityConverter<T> converter) {
        List<T> entities = new ArrayList<>();
        for (String[] row : rawData) {
            entities.add(converter.convert(row));
        }
        return entities;
    }

    public interface EntityConverter<T> {
        T convert(String[] data);
    }
}
