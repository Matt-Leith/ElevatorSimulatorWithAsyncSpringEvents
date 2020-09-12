package com.example.elevator.controller;

import com.example.elevator.model.Elevator;
import com.example.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ElevatorController {
    @Autowired
    private ElevatorService elevatorService;

    @RequestMapping(value = "getElevators", method = RequestMethod.GET)
    @ResponseBody
    public List<Elevator> getAllElevators() {
        return elevatorService.getElevators();
    }

}
