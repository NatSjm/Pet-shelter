package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Animal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PetShelterService {
    private static final String FILE_PATH = "animals.json";
    private List<Animal> animals;
    private ObjectMapper objectMapper;

    public PetShelterService() {
        animals = new ArrayList<>();
        objectMapper = new ObjectMapper();
        loadAnimalsFromFile();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        saveAnimalsToFile();
    }

    public void removeAnimal(String animalName) {
        animals.removeIf(animal -> animal.getName().equalsIgnoreCase(animalName));
        saveAnimalsToFile();
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    private void loadAnimalsFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                animals = objectMapper.readValue(file, new TypeReference<List<Animal>>() {
                });
            } catch (IOException e) {
                handleFileOperationError("Error reading data from file", e);
            }
        }
    }

    public void saveAnimalsToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), animals);
        } catch (IOException e) {
            handleFileOperationError("Error writing data to file", e);
        }
    }

    private void handleFileOperationError(String message, IOException e) {
        System.err.println(message + ": " + e.getMessage());
    }
}