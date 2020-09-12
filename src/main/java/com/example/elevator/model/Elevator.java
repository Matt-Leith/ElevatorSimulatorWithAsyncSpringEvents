package com.example.elevator.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Elevator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(targetEntity = Trip.class)
    private List<Trip> tripQueue = new ArrayList<>();

    private Long currentFloor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Trip> getTripQueue() {
        return tripQueue;
    }

    public Long getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Long currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Integer getTripQueueSize() {
        return tripQueue.size();
    }

    public Integer getActiveTripsQueueSize() {
        return tripQueue.stream().filter(x -> x.getArrivalAtDestinationTime() == null).collect(Collectors.toList()).size();
    }
}
