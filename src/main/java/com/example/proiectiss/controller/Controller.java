package com.example.proiectiss.controller;

import com.example.proiectiss.service.Service;
import com.example.proiectiss.utils.Observer;

public interface Controller extends Observer {
    void setService(Service service);
}
