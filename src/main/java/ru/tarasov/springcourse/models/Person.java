package ru.tarasov.springcourse.models;


import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size (min = 2, max = 30, message = "name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "age should be greater than 0")
    private int age;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;
    //Странна, Город, индекс(6символов)
    @Pattern(regexp ="[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "your adress should be in this form: Country, City, Postal code (6 digits)")
    private String adress;


    public Person(int id, String name, int age, String email, String adress) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.adress = adress;
    }

    public Person(){
    }


    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getAdress(){return adress;}
    public void setAdress(String adress) {this.adress = adress;}
}
