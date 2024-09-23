package com.example.proiectiss.model;

import java.io.Serializable;

public class Identifiable<ID extends Serializable> {
    ID id;

    public Identifiable(ID id){
        this.id = id;
    }

    public ID getId(){
        return id;
    }

    public void setId(ID id){
        this.id = id;
    }
}
