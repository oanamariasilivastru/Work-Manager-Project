package com.example.proiectiss.model;

public class Admin extends Employee{

    private AdminFunction adminFunction;
    public Admin(Long id, String username, String password, AdminFunction adminFunction){
        super(id, username, password);
        this.adminFunction = adminFunction;
    }

    public Admin(Long id, String username, String password){
        super(id, username, password);
    }

    public AdminFunction getAdminFunction(){
        return adminFunction;
    }

    public void setAdminFunction(AdminFunction adminFunction){
        this.adminFunction = adminFunction;
    }
}
