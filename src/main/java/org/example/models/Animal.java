package org.example.models;

import lombok.Data;
import org.example.enums.Gender;

@Data
public class Animal {
    private String name;
    private String species;
    private String breed;
    private Gender gender;
    private int age;
}