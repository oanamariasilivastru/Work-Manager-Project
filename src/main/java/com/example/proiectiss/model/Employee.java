package com.example.proiectiss.model;

public class Employee extends Identifiable<Long>{
    public String password;
    public String name;

    public Employee(Long id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
