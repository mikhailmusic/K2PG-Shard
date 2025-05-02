package rut.miit.k2pgshard.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class User extends BaseEntity{
    private String firstName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String country;

    public User(String firstName, String email, String phoneNumber, LocalDate birthDate, String country) {
        setFirstName(firstName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setBirthDate(birthDate);
        setCountry(country);
    }

    protected User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().length() < 2) {
            throw new IllegalArgumentException("First Name must be at least 2 characters and not null");
        }
        this.firstName = firstName;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number must not be blank or null");
        }
        if (!phoneNumber.matches("\\+?[0-9]+")) {
            throw new IllegalArgumentException("Phone number must only contain digits and optional '+'");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date must not be null");
        }
        if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 14) {
            throw new IllegalArgumentException("Person must be at least 14 years old");
        }
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank");
        }
        if (!email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty or whitespace");
        }
        if (country.length() > 100) {
            throw new IllegalArgumentException("Country name is too long");
        }
        this.country = country;
    }
}