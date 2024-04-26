package org.example;

import org.example.enums.Gender;
import org.example.models.Animal;
import org.example.services.PetShelterService;

import java.util.Scanner;

public class PetShelterApp {
    private final PetShelterService petShelterService;
    private final Scanner scanner;

    public PetShelterApp() {
        petShelterService = new PetShelterService();
        scanner = new Scanner(System.in);
    }

    private void run() {
        int choice;
        while (true) {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addAnimal();
                    break;
                case 2:
                    showAnimals();
                    break;
                case 3:
                    takePetFromShelter();
                    break;
                case 4:
                    petShelterService.saveAnimalsToFile();
                    System.out.println("Good bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("Pet Shelter Menu:");
        System.out.println("1. Add an animal");
        System.out.println("2. Show all animals");
        System.out.println("3. Take a pet from the shelter");
        System.out.println("4. Exit");
        System.out.print("Enter your choice number: ");
    }

    private void addAnimal() {
        System.out.println("Adding a new animal:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Breed: ");
        String breed = scanner.nextLine();
        System.out.print("Species: ");
        String species = scanner.nextLine();

        Gender gender = null;
        boolean validGenderInput = false;
        do {
            System.out.print("Gender (MALE, FEMALE, UNKNOWN): ");
            String genderInput = scanner.nextLine();
            try {
                gender = Gender.valueOf(genderInput.toUpperCase());
                validGenderInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender. Please enter MALE, FEMALE, or UNKNOWN.");
            }
        } while (!validGenderInput);

        int age = 0;
        boolean validAgeInput = false;
        do {
            try {
                System.out.print("Age: ");
                age = Integer.parseInt(scanner.nextLine());
                validAgeInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a valid integer.");
            }
        } while (!validAgeInput);

        Animal newAnimal = new Animal();
        newAnimal.setName(name);
        newAnimal.setBreed(breed);
        newAnimal.setSpecies(species);
        newAnimal.setGender(gender);
        newAnimal.setAge(age);

        petShelterService.addAnimal(newAnimal);
        System.out.println("The animal has been added to the shelter.");
    }

    private void showAnimals() {
        System.out.println("List of animals in the shelter:");
        petShelterService.getAnimals().forEach(System.out::println);
    }

    private void takePetFromShelter() {
        System.out.println("Removing an animal from the shelter:");

        if (petShelterService.getAnimals().isEmpty()) {
            System.out.println("The shelter is empty. No animals to remove.");
            return;
        }

        petShelterService.getAnimals().forEach(animal -> System.out.println(animal.getName()));
        System.out.print("Choose the name of the animal to remove: ");
        String animalName = scanner.nextLine();

        Animal selectedAnimal = petShelterService.getAnimals().stream()
                .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                .findFirst()
                .orElse(null);

        if (selectedAnimal == null) {
            System.out.println("Animal with the specified name not found in the shelter.");
        } else {
            petShelterService.removeAnimal(animalName);
            System.out.println("The selected animal has been removed from the shelter.");
        }
    }

    public static void main(String[] args) {
        PetShelterApp petShelterApp = new PetShelterApp();
        petShelterApp.run();
    }
}