package com.example.server;
import jakarta.persistence.*;
@Entity
@Table(name = "users")

public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private String name;
    private String surname;
    private String birthdate;
    private String licensenumber;
    private String email;
    private String password;
    
    public User() {
    }

    // Constructor
    public User(Integer id, String name, String surname, String birthdate, String licensenumber, String email, String password) {
    	this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.licensenumber = licensenumber;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getLicenseNumber() {
        return licensenumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licensenumber = licenseNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", licenseNumber='" + licensenumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
