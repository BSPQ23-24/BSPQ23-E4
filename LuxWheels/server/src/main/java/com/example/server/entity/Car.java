package com.example.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer licensePlate;

    @Enumerated(EnumType.STRING) // Maps the ENUM type in SQL to a Java Enum
    private CarCondition carCondition;

    private String brand;
    private String model;

    @Column(columnDefinition = "DATE")
    private String year; // Consider using java.time.LocalDate for date fields

    private String location;

    @ManyToOne // Assuming many cars can be owned by one user
    @JoinColumn(name = "UserID", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate=" + licensePlate +
                ", carCondition=" + carCondition +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", location='" + location + '\'' +
                ", user=" + (user != null ? user.getId() : "null") + // Safely accessing user ID
                '}';
    }

    // Enum to represent the CarCondition SQL ENUM
    public enum CarCondition {
        bad, standard, good
    }
}